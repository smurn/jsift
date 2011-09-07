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
public class Subsampler implements DownScaler {

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
     * @return Subsampled image with the same sigma.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    @Override
    public Image downScale(final Image image) {
        if (image == null) {
            throw new NullPointerException("image must not be null");
        }
        if (image.getWidth() < 1 || image.getHeight() < 1) {
            throw new IllegalArgumentException("image must be at least"
                    + "one pixel in width and height.");
        }
        int width = (image.getWidth() + 1) / 2;
        int height = (image.getHeight() + 1) / 2;

        Image result = new Image(height, width, image.getSigma(),
                image.getScale() / 2.0,
                image.getOffsetX() / 2.0,
                image.getOffsetY() / 2.0);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result.setPixel(row, col, image.getPixel(2 * row, 2 * col));
            }
        }

        return result;
    }
}
