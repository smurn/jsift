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
 * Interface for factories that create {@link ScaleSpace} instances.
 */
public interface ScaleSpaceFactory {

    /**
     * Creates the scale space for an image using default settings.
     * @param image Image to build the scale space for.
     * @return Scale space of the given image.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    ScaleSpace create(Image image);

    /**
     * Creates the scale space for an image.
     * @param image Image to build the scale space for.
     * @param scalesPerOctave Number of scales per octave. Lowe suggests
     * to use three.
     * @param initialSigma Sigma of the first scale in the first octave. Lowe
     * suggests 1.6.
     * @param upScaler Algorithm to increase the image size. Lowe suggests
     * {@link LinearUpScaler}.
     * @param downScaler Algorithm to decrease the image size. Lowe suggests
     * {@link SubsamplerImpl}.
     * @param filter Algorithm to filter out high-frequency components. Lowe
     * suggests {@link GaussianFilter}.
     * @param octaveFactory Factory to create the octaves with 
     * (typically {@link OctaveFactoryImpl}).
     * @return Scale space of the given image.
     * @throws NullPointerException if {@code image} or one of the algorithms is
     * {@code null}.
     * @throws IllegalArgumentException if {@code scalesPerOctave} is smaller
     * than one, {@code originalBlur} is not stricly positive or
     * {@code initialBlur} is smaller than {@code 2*originalBlur}.
     */
    ScaleSpace create(Image image, int scalesPerOctave, double initialSigma,
            UpScaler upScaler, DownScaler downScaler,
            LowPassFilter filter, OctaveFactory octaveFactory);
}