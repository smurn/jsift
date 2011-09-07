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

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for scale-spaces.
 */
public class ScaleSpaceFactoryImpl implements ScaleSpaceFactory {

    /** Number of scales per octave as proposed by Lowe. */
    private static final int LOWE_SCALES_PER_OCTAVE = 3;
    /** Blur of the first scale-level as proposed by Lowe. */
    private static final double LOWE_INITIAL_SIGMA = 0.8;

    /**
     * Creates a scale space for an image using the parameters and algorithms
     * proposed in Lowe's paper.
     * @param image Image to build the scale space for.
     * @return Scale space of the given image.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if the image is smaller than 2x2 pixels.
     */
    @Override
    public ScaleSpace create(final Image image) {
        return create(
                image,
                LOWE_SCALES_PER_OCTAVE,
                LOWE_INITIAL_SIGMA,
                new LinearUpScaler(),
                new Subsampler(),
                new GaussianFilter(),
                new OctaveFactoryImpl());
    }

    /**
     * Creates the scale space for an image.
     * @param image Image to build the scale space for.
     * @param scalesPerOctave Number of scales per octave. Lowe suggests
     * to use three.
     * @param initialSigma Sigma of the first scale in the first octave. Lowe
     * suggests 1.6, but he's referring to an upscaled image. We are
     * interpreting all sigma values in reference to the original image. 
     * So the corresponing value to use here is 0.8.
     * @param upScaler Algorithm to increase the image size. Lowe suggests
     * {@link LinearUpScaler}.
     * @param downScaler Algorithm to decrease the image size. Lowe suggests
     * {@link Subsampler}.
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
    @Override
    public ScaleSpace create(final Image image, final int scalesPerOctave,
            final double initialSigma,
            final UpScaler upScaler, final DownScaler downScaler,
            final LowPassFilter filter, final OctaveFactory octaveFactory) {

        if (image == null) {
            throw new NullPointerException("image must not be null");
        }
        if (upScaler == null) {
            throw new NullPointerException("upScaler must not be null");
        }
        if (downScaler == null) {
            throw new NullPointerException("downScaler must not be null");
        }
        if (filter == null) {
            throw new NullPointerException("filter must not be null");
        }
        if (octaveFactory == null) {
            throw new NullPointerException("octaveFactory must not be null");
        }
        if (scalesPerOctave < 1) {
            throw new IllegalArgumentException(
                    "Need at least one scale per octave");
        }
        if (initialSigma < image.getSigma()) {
            throw new IllegalArgumentException("initial sigma must be greater"
                    + " or equal the original sigma.");
        }

        // upscale the image and apply the blur we need for the initial blur.
        Image startImage = upScaler.upScale(image);
        startImage = filter.filter(startImage, initialSigma);

        List<Octave> octaves = new ArrayList<Octave>();
        while (startImage.getWidth() > 0 && startImage.getHeight() > 0) {
            Octave octave = octaveFactory.create(startImage, scalesPerOctave,
                    filter);
            octaves.add(octave);

            // get the scale-image which has twice the sigma as the bases for
            // the next octave.
            Image twiceBlurred = octave.getScaleImages().get(scalesPerOctave);
            startImage = downScaler.downScale(twiceBlurred);
        }

        return new ScaleSpace(octaves);
    }
}
