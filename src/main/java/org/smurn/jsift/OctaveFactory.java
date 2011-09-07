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
 * Interface for factories that create {@link Octave} instances.
 */
public interface OctaveFactory {

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
    Octave create(Image image, int scalesPerOctave, LowPassFilter filter);
}
