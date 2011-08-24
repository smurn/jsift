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
 * Reduces the size of an image by half.
 */
public class Subsampler {

    /**
     * Creates an instance.
     */
    public Subsampler() {
        // empty
    }

    /**
     * Reduces the size of the image by selecting each second pixel.
     * <p>The even pixels are taken for the resulting image.</p>
     * <p>The size of the resulting image is {@code floor((n+1)/2)}.</p>
     * @param image Image to subsample.
     * @return Subsampled image.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image subsample(final Image image) {
        throw new UnsupportedOperationException("not implemented");
    }
}
