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
 * Two dimensional gaussian filter.
 * <p>The standard deviation is set in the constructor to allow
 * reuse of the pre-calculated kernel.</p>
 * <p>Pixels outside the image are interpreted as transparent.</p>
 */
public final class GaussianFilter {

    /**
     * Creates an instance.
     * @param sigma Standard deviation of the gaussian kernel.
     * @throws IllegalArgumentException if {@code sigma} is not strictly 
     * positive.
     */
    public GaussianFilter(final double sigma) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Creates a filtered copy of the given image.
     * @param image Image to filter. Ist not modified.
     * @return Blurred image.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image filter(final Image image) {
        throw new UnsupportedOperationException("not implemented");
    }
}
