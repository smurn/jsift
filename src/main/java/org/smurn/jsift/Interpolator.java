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
public final class Interpolator {

    /**
     * Increases the image size by a factor of two.
     * <p>The scaling is such that the even pixels of the resulting image
     * are identical to the corresponding pixels in the source image.
     * The odd pixels are linearly interpolated from its two or four
     * neighboors in the source image.</p>
     * <p>The width of the resulting image is {@code 2*width-1}. The
     * same formula applies for the height.</p>
     * @param image Image to interpolate.
     * @return Interpolated image with double the size.
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if the image is not at least
     * one pixel in height and width.
     */
    public Image interpolate(final Image image) {
        if (image == null) {
            throw new NullPointerException("image must not be null.");
        }
        if (image.getWidth() == 0 || image.getHeight() == 0) {
            throw new IllegalArgumentException("image must consist of at least "
                    + "one pixel.");
        }

        int width = image.getWidth() * 2 - 1;
        int height = image.getHeight() * 2 - 1;

        Image scaled = new Image(height, width);

        for (int row = 0; row < scaled.getHeight(); row++) {
            for (int col = 0; col < scaled.getWidth(); col++) {

                int sourceRow = row / 2;
                int sourceCol = col / 2;

                float sum = image.getPixel(sourceRow, sourceCol);
                int weight = 1;

                if (2 * sourceRow != row) {
                    sum += image.getPixel(sourceRow + 1, sourceCol);
                    weight++;
                }
                if (2 * sourceCol != col) {
                    sum += image.getPixel(sourceRow, sourceCol + 1);
                    weight++;
                }
                if (2 * sourceRow != row && 2 * sourceCol != col) {
                    sum += image.getPixel(sourceRow + 1, sourceCol + 1);
                    weight++;
                }
                scaled.setPixel(row, col, sum / weight);
            }
        }
        return scaled;
    }
}
