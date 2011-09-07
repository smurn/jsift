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
 *
 * @author Stefan C. Mueller
 */
public class OctaveFactoryImpl implements OctaveFactory {

    private static final int ADDITIONAL_SCALES = 2;

    /**
     * Creates an octave.
     * @param image Scale-image with the lowest scale of this octave.
     * @param scalesPerOctave Number of scales per octave.
     * @param filter Algorithm to filter out high-frequency components.
     * @return Octave built from the given image.
     * @throws NullPointerException if {@code image} or one of the algorithms is
     * {@code null}.
     * @throws IllegalArgumentException if {@code scalesPerOctave} is smaller
     * than one or if the initial blur is not larger than zero.
     */
    @Override
    public Octave create(final Image image, final int scalesPerOctave,
            final LowPassFilter filter) {

        if (image == null) {
            throw new NullPointerException("Image must not be null");
        }
        if (filter == null) {
            throw new NullPointerException("Filter must not be null");
        }
        if (scalesPerOctave < 1) {
            throw new IllegalArgumentException(
                    "Need at least one scale per octave");
        }

        List<Image> scaleImages = new ArrayList<Image>(
                scalesPerOctave + ADDITIONAL_SCALES + 1);
        scaleImages.add(image);
        for (int i = 1; i < scalesPerOctave + ADDITIONAL_SCALES + 1; i++) {
            double nextSigma = image.getSigma()
                    * Math.pow(2.0, (double) i / scalesPerOctave);

            Image scaleImage = filter.filter(scaleImages.get(i - 1), nextSigma);
            scaleImages.add(scaleImage);
        }

        List<Image> diffOfGaussian = new ArrayList<Image>(scalesPerOctave + 2);
        for (int i = 0; i < scalesPerOctave + ADDITIONAL_SCALES; i++) {
            Image lower = scaleImages.get(i);
            Image higher = scaleImages.get(i + 1);
            Image dog = higher.subtract(lower);
            diffOfGaussian.add(dog);
        }

        return new Octave(scaleImages, diffOfGaussian);
    }
}
