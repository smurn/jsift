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

    /**
     * Creates an instance.
     * @param octaves Octaves of this scale-space.
     * @throws NullPointerException if {@code octave} is {@code null}.
     * @throws IllegalArgumentException if there is not at least one octave.
     */
    public ScaleSpace(List<Octave> octaves) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the octaves of this scale-space.
     * @return Immutable list of octaves.
     */
    public List<Octave> getOctaves() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Get the sets of three adjacent difference-of-gaussian images together
     * with the scaled images they depend on.
     * @return Immutable list of scale-levels.
     */
    public List<ScaleLevel> getScaleLevel() {
        throw new UnsupportedOperationException("not implemented");
    }
}
