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
public final class GaussianFilter {

    private static final int WINDOW_SIZE_FACTOR = 4;
    private static final double PRECISION = Double.MIN_NORMAL;
    private final double[] kernel;
    private final double[] cumulativeKernel;

    /**
     * Creates an instance.
     * @param sigma Standard deviation of the gaussian kernel.
     * @throws IllegalArgumentException if {@code sigma} is not strictly 
     * positive.
     */
    public GaussianFilter(final double sigma) {
        if (sigma <= 0) {
            throw new IllegalArgumentException("sigma must not be zero or "
                    + "negative.");
        }
        try {
            kernel = buildKernel(sigma);
            cumulativeKernel = cumulativeSum(kernel);
        } catch (MathException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a filtered copy of the given image.
     * @param image Image to filter. Ist not modified.
     * @return Blurred image.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image filter(final Image image) {
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
     * @throws MathException if the approximation failed.
     */
    private static double[] buildKernel(final double sigma) 
            throws MathException {

        int windowSize = (int) Math.ceil(WINDOW_SIZE_FACTOR * sigma);

        NormalDistributionImpl ndist = new NormalDistributionImpl(0.0, sigma,
                PRECISION);

        double[] kernel = new double[2 * windowSize + 1];

        double sum = 0;
        for (int i = 0; i < kernel.length; i++) {
            double x = i - windowSize;
            kernel[i] = ndist.cumulativeProbability(x - 0.5, x + 0.5);
            sum += kernel[i];
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
