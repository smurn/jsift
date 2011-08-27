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

import java.util.List;

/**
 * Represents a scale interval in the scale-space where the scale doubles.
 * <p>All images in an octave have the same width and height.</p>
 */
public final class Octave {

    /**
     * Creates an instance.
     * @param baseScale Scale of the scale-image with the lowest scale in this octave.
     * @param scaleImages Scale-images.
     * @param doGs Difference-of-gaussian images.
     * @throws NullPointerException if one of the parameters is {@code null}.
     * @throws IllegalArgumentException if there is not at least three scale
     * images, if the number of Difference-of-gaussian images is not one less
     * than the number of scale-images, if not all images are of equal width and height
     * or if {@code baseScale} is not strictly positive.
     */
    public Octave(double baseScale, List<Image> scaleImages, List<Image> doGs){
        throw new UnsupportedOperationException("not implemented");
    }
    
    /**
     * Scale of the scale-image with the lowest scale in this octave.
     * @return Base scale of this octave.
     */
    public double getBaseScale() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the number of scales per octave. 
     */
    public int getScalesPerOctave() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the scale-image at the scales of this octave.
     * Each image's scale is {@code b*2^(i/s)} where
     * {@code s=getScalesPerOctave()}, {@code b=getBaseScale()}.
     * @return Immutable list with {@code getScalesPerOctave()+3} images.
     */
    public List<Image> getScaleImages() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the difference-of-gaussian images of this octave.
     * The image with index {@code i} is the difference from the scale-image
     * with index {@code i+1} and {@code i}.
     * Each image's scale is {@code b*2^((i+0.5)/s)} where
     * {@code s=getScalesPerOctave()}, {@code b=getBaseScale()}.
     * @return Immutable list with {@code getScalesPerOctave()+2} images.
     */
    public List<Image> getDifferenceOfGaussians() {
        throw new UnsupportedOperationException("not implemented");
    }
}
