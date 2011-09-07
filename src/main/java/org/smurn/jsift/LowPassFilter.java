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
 * Two-dimensional low-pass filter.
 */
public interface LowPassFilter {

    /**
     * Creates a filtered copy of the given image.
     * <p>It is assumed that filtering with a sigma of 2 is sufficient
     * for later subsampling (see {@link Subsampler}) without significant loss
     * of information.</p>
     * @param image Image to filter.
     * @param sigma Sigma of the resulting image. THIS IS NOT the 'Radius' of
     * the filter. The filter size is calculated such that the resulting
     * image will have the desired sigma.
     * @return Filtered image with the given sigma.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if {@code sigma} is smaller then the
     * input image's sigma.
     */
    Image filter(Image image, double sigma);
}
