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
 * Increases the size of an image by a factor of two.
 */
public interface UpScaler {

    /**
     * Increases the image size by a factor of two.
     * The exact size of the resulting image is implementation dependent, but it
     * is garanteed that both width and height of the resulting image is
     * larger or equal than of the given image.
     * @param image Image to upScale.
     * @return Image with double the size and the same sigma as the input image.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    Image upScale(Image image);
}
