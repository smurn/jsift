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

/**
 * Three dimensional extension of an image using scale as the third dimension.
 * <p>The scale is usually given as sigma, the amount of blur in the image. Due
 * to the exponential sampling of the scale this class represents the scale
 * by the scale-exponent {@code se}: {@code sigma = sigma_0*alpha^se} where
 * {@code sigma_0} is the initial blur (see constructor)
 * and {@code alpha=2^(-2*s)} where {@code s} is the number of scales per 
 * octave (see constructor). This defintion ensures that for every multiple of
 * {@code 2s} sigma increases by a factor of 2.</p>
 * <p>Images representing the higher scales (less detail) are stored
 * in smaller image sizes to reduce the memory requirement and processing
 * time.</p>
 * <p>This class stores the blurred images as the various scales as well
 * as the difference-of-gaussians (DoG) that result from them. The
 * algorithm works with three adjacent DoG images simultaniously that all
 * need to have the same width and height. {@link #getScaleLevel(int)}
 * provides such a set of three DoG images.</p>
 */
public final class ScaleSpace {

    /** Number of scales per octave as proposed by Lowe. */
    private static final int LOWE_SCALES_PER_OCTAVE = 3;
    /** Estimation of the blur in the input image as proposed by Lowe. */
    private static final double LOWE_ORIGINAL_BLUR = 0.5;
    /** Blur of the first scale-level as proposed by Lowe. */
    private static final double LOWE_INITIAL_BLUR = 1.6;

    /**
     * Creates a scale space for an image using the parameters proposed
     * in Lowe's paper.
     * @param image Image to build the scale space for.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if the image is smaller than 2x2 pixels.
     */
    public ScaleSpace(final Image image) {
        this(image,
                LOWE_SCALES_PER_OCTAVE,
                LOWE_ORIGINAL_BLUR,
                LOWE_INITIAL_BLUR);
    }

    /**
     * Creates the scale space for an image.
     * @param image Image to build the scale space for.
     * @param scalesPerOctave Number of scales per octave. Lowe suggests
     * to use three.
     * @param originalBlur Estimation of the blurriness of the input image.
     * Lowe suggests to use 0.5, the minimum required to avoid aliasing.
     * @param initialBlur Initial blur to apply. Lowe suggests 1.6.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if {@code scalesPerOctave} is smaller
     * than one, {@code originalBlur} is not stricly positive,
     * {@code initialBlur} is smaller than {@code 2*originalBlur} or if the
     * image is smaller than 2x2 pixels.
     */
    public ScaleSpace(final Image image, final int scalesPerOctave,
            final double originalBlur, final double initialBlur) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the largest base-scale-exponent available.
     * @return Maximual base-scale-exponent.
     */
    public int maxBaseScaleExponent() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Get a set of three adjacent difference-of-gaussian images together
     * with the scaled images they depend on.
     * @param baseScaleExponent Base scale-exponent of the scale level.
     * @return Scale-level.
     * @throws IndexOutOfBoundsException if {@code baseScaleExponent } is
     * negative or larger than {@link #maxBaseScaleExponent()}.
     */
    public ScaleLevel getScaleLevel(final int baseScaleExponent) {
        throw new UnsupportedOperationException("not implemented");
    }
}
