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
 *
 * @author Stefan C. Mueller
 */
public class ScaleSpaceFactory {

    /** Number of scales per octave as proposed by Lowe. */
    private static final int LOWE_SCALES_PER_OCTAVE = 3;
    /** Estimation of the blur in the input image as proposed by Lowe. */
    private static final double LOWE_ORIGINAL_BLUR = 0.5;
    /** Blur of the first scale-level as proposed by Lowe. */
    private static final double LOWE_INITIAL_BLUR = 1.6;

    /**
     * Creates a scale space for an image using the parameters and algorithms
     * proposed in Lowe's paper.
     * @param image Image to build the scale space for.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if the image is smaller than 2x2 pixels.
     */
    public ScaleSpace create(final Image image) {
        return create(
                image,
                LOWE_SCALES_PER_OCTAVE,
                LOWE_ORIGINAL_BLUR,
                LOWE_INITIAL_BLUR,
                new LinearUpScalerImpl(),
                new SubsamplerImpl(),
                new GaussianFilterImpl());
    }

    /**
     * Creates the scale space for an image.
     * @param image Image to build the scale space for.
     * @param scalesPerOctave Number of scales per octave. Lowe suggests
     * to use three.
     * @param originalBlur Estimation of the blurriness of the input image.
     * Lowe suggests to use 0.5, the minimum required to avoid aliasing.
     * @param initialBlur Initial blur to apply. Lowe suggests 1.6.
     * @param upScaler Algorithm to increase the image size. Lowe suggests
     * {@link LinearUpScalerImpl}.
     * @param downScaler Algorithm to decrease the image size. Lowe suggests
     * {@link SubsamplerImpl}.
     * @param filter Algorithm to filter out high-frequency components. Lowe
     * suggests {@link GaussianFilterImpl}.
     * @throws NullPointerException if {@code image} or one of the algorithms is
     * {@code null}.
     * @throws IllegalArgumentException if {@code scalesPerOctave} is smaller
     * than one, {@code originalBlur} is not stricly positive,
     * {@code initialBlur} is smaller than {@code 2*originalBlur} or if the
     * image is smaller than 2x2 pixels.
     */
    public ScaleSpace create(final Image image, final int scalesPerOctave,
            final double originalBlur, final double initialBlur,
            UpScaler upScaler, DownScaler downScaler, LowPassFilter filter) {
        throw new UnsupportedOperationException("not implemented");
    }
}
