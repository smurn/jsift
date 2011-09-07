/*
 * Copyright 2011 Stefan C. Mueller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smurn.jsift;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

/**
 * Two dimensional gaussian filter.
 * <p>The standard deviation is set in the constructor to allow
 * reuse of the pre-calculated kernel.</p>
 * <p>Pixels outside the image are interpreted as transparent.</p>
 */
public final class GaussianFilter implements LowPassFilter {

    private static final int WINDOW_SIZE_FACTOR = 4;
    private static final double PRECISION = Double.MIN_NORMAL;

    /**
     * Creates an instance.
     */
    public GaussianFilter() {
        // empty
    }

    /**
     * Creates a filtered copy of the given image.
     * @param image Image to filter.
     * @param sigma Sigma of the resulting image. THIS IS NOT the 'Radius' of
     * the filter. The filter size is calculated such that the resulting
     * image will have the desired sigma.
     * @return Blurred image.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if {@code sigma} is smaller then the
     * input image's sigma.
     */
    @Override
    public Image filter(final Image image, final double sigma) {
        if (image == null) {
            throw new NullPointerException("image must not be null");
        }
        if (sigma < image.getSigma()) {
            throw new IllegalArgumentException("cannot reduce sigma");
        }

        // The sigma parameter and the image's sigma value are relative to the
        // original image, so we need to transform this into
        // the coordinate space of this image.
        final double scale = image.getScale();
        final double imageSigma = image.getSigma() * scale;
        final double targetSigma = sigma * scale;
        double filterSigma = Math.sqrt(targetSigma * targetSigma
                - imageSigma * imageSigma);

        if (filterSigma == 0.0) {
            // Dirac delta function, output is input
            return new Image(image);
        }

        double[] kernel = buildKernel(filterSigma);
        double[] cumulativeKernel = cumulativeSum(kernel);
        int window = (kernel.length - 1) / 2;

        // horizontal pass
        double[][] horizontal = new double[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {

                int kernelFrom = Math.max(window - col, 0);
                int kernelTo = window
                        + Math.min(image.getWidth() - 1 - col, window);

                double value = 0;
                for (int i = kernelFrom; i <= kernelTo; i++) {
                    value += kernel[i] * image.getPixel(row, col - window + i);
                }
                double weight = cumulativeKernel[kernelTo + 1]
                        - cumulativeKernel[kernelFrom];
                value /= weight;
                horizontal[row][col] = value;
            }
        }

        Image result = new Image(image.getHeight(), image.getWidth());

        // vertical pass (in-place)
        double[][] vertical = new double[image.getHeight()][image.getWidth()];
        for (int col = 0; col < image.getWidth(); col++) {
            for (int row = 0; row < image.getHeight(); row++) {

                int kernelFrom = Math.max(window - row, 0);
                int kernelTo = window
                        + Math.min(image.getHeight() - 1 - row, window);

                double value = 0;
                for (int i = kernelFrom; i <= kernelTo; i++) {
                    value += kernel[i] * horizontal[row - window + i][col];
                }
                double weight = cumulativeKernel[kernelTo + 1]
                        - cumulativeKernel[kernelFrom];
                value /= weight;
                vertical[row][col] = value;

            }
        }

        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                result.setPixel(row, col, (float) vertical[row][col]);
            }
        }

        return result;
    }

    /**
     * Builds a one dimensional gaussian kernel.
     * @param sigma Standard deviation of the kernel.
     * @return Discretized kernel. Array length is odd, kernel is centered.
     */
    private static double[] buildKernel(final double sigma) {

        int windowSize = (int) Math.ceil(WINDOW_SIZE_FACTOR * sigma);

        NormalDistributionImpl ndist = new NormalDistributionImpl(0.0, sigma,
                PRECISION);

        double[] kernel = new double[2 * windowSize + 1];
        double sum = 0;
        try {
            for (int i = 0; i < kernel.length; i++) {
                double x = i - windowSize;
                kernel[i] = ndist.cumulativeProbability(x - 0.5, x + 0.5);
                sum += kernel[i];
            }
        } catch (MathException ex) {
            throw new RuntimeException(ex);
        }
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] /= sum;
        }

        return kernel;
    }

    /**
     * Builds the cumulative sum of the kernel.
     * @param kernel Discretized kernel.
     * @return Cumulative sum. The element i is the sum of the kernel
     * elements 0 to i-1 (inclusive).
     */
    private static double[] cumulativeSum(final double[] kernel) {
        double[] csum = new double[kernel.length + 1];
        csum[0] = 0.0;
        for (int i = 0; i < kernel.length; i++) {
            csum[i + 1] = csum[i] + kernel[i];
        }
        return csum;
    }
}
