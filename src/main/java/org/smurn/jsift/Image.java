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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Gray-scale image with 32-bit floating point precision.
 * A pixel value of 0 is interpreted as black, a value of 1 as white. But
 * the value range is not restricted to those values.
 */
public final class Image {

    private final float[][] pixels;
    private final int height;
    private final int width;
    private static final float UFLOAT_MAX = 65535.0f;

    /**
     * Creates an image of the given size where all pixels have the value zero.
     * @param height Number of rows of the image.
     * @param width Number of rows of the image.
     * @throws IllegalArgumentException if {@code rows} or {@code cols} are
     * negative.
     */
    public Image(final int height, final int width) {
        if (height < 0) {
            throw new IllegalArgumentException("Cannot create image"
                    + " with " + height + " rows.");
        }
        if (width < 0) {
            throw new IllegalArgumentException("Cannot create image"
                    + " with " + height + " columns.");
        }
        this.pixels = new float[height][width];
        this.height = height;
        this.width = width;
    }

    /**
     * Copy constructor.
     * @param image Image to copy.
     */
    public Image(final Image image) {
        this(image.pixels);
    }

    /**
     * Creates an image from the given pixel values.
     * @param pixels Two dimensional array containing the pixels. The array
     * elements are interpreted as {@code pixels[row][column]}. Use
     * {@link #Image(float[][], boolean)} to read images in
     * {@code pixels[row][column]} order.
     * @throws NullPointerException if {@code pixels} or one of the sub-arrays
     * is {@code null}.
     * @throws IllegalArgumentException if the sub-arrays do not all have
     * equal length.
     */
    public Image(final float[][] pixels) {
        this(pixels, true);
    }

    /**
     * Creates an image from the given pixel values.
     * @param pixels Two dimensional array containing the pixels.
     * @param firstIndexIsRow If {@code true} the elements of the array
     * are interpreted as {@code pixels[row][column]}, otherwise as
     * {@code pixels[column][row]}. If {@code true} is passed, this
     * method behaves identically to {@link #Image(float[][])}.
     * @throws NullPointerException if {@code pixels} or one of the sub-arrays
     * is {@code null}.
     * @throws IllegalArgumentException if the sub-arrays do not all have
     * equal length.
     */
    public Image(final float[][] pixels, final boolean firstIndexIsRow) {
        if (pixels == null) {
            throw new NullPointerException("pixels cannot be null.");
        }
        if (firstIndexIsRow) {
            this.height = pixels.length;
            if (this.height != 0) {
                this.width = pixels[0].length;
            } else {
                this.width = 0;
            }

            this.pixels = new float[this.height][this.width];
            for (int row = 0; row < this.height; row++) {
                if (pixels[row].length != this.width) {
                    throw new IllegalArgumentException("Row " + row + " has "
                            + pixels[row].length + " columns but row 0 has "
                            + this.width + " columns.");
                }
                System.arraycopy(pixels[row], 0, this.pixels[row],
                        0, this.width);
            }
        } else {
            this.width = pixels.length;
            if (this.width != 0) {
                this.height = pixels[0].length;
            } else {
                this.height = 0;
            }

            this.pixels = new float[this.height][this.width];
            for (int col = 0; col < this.width; col++) {
                if (pixels[col].length != this.height) {
                    throw new IllegalArgumentException("Column " + col + " has "
                            + pixels[col].length + " rows but column 0 has "
                            + this.width + " rows.");
                }
                for (int row = 0; row < this.height; row++) {
                    this.pixels[row][col] = pixels[col][row];
                }
            }
        }
    }

    /**
     * Creates a copy from the given AWT image.
     * <p>If the given image is not a grayscale image it will be converted.</p>
     * @param image Image to copy.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image(final java.awt.Image image) {
        BlockingImageObserver observer = new BlockingImageObserver();

        int imageHeight = image.getHeight(observer);
        if (imageHeight < 0) {
            observer.waitForImageToComplete();
            imageHeight = image.getHeight(observer);
        }

        int imageWidth = image.getWidth(observer);
        if (imageWidth < 0) {
            observer.waitForImageToComplete();
            imageWidth = image.getWidth(observer);
        }

        BufferedImage grayImage = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_USHORT_GRAY);
        Graphics2D g = grayImage.createGraphics();

        if (!g.drawImage(image, 0, 0, observer)) {
            observer.waitForImageToComplete();
            g.drawImage(image, 0, 0, null);
        }
        g.dispose();

        this.height = grayImage.getHeight();
        this.width = grayImage.getWidth();
        this.pixels = new float[height][width];

        Raster raster = grayImage.getRaster();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                float sample = raster.getSampleFloat(col, row, 0) / UFLOAT_MAX;
                this.pixels[row][col] = sample;
            }
        }
    }

    /**
     * Gets the number of rows.
     * @return Number of rows of this image. Never negative.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the number of columns.
     * @return Number of columns of this image. Never negative.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the value of a pixel.
     * @param row Row of the pixel.
     * @param column Column of the pixel.
     * @return Gray-scale value of the pixel (0 for black, 1 for white).
     * @throws IndexOutOfBoundsException if {@code row} or {@code column}
     * are negative or larger-equals the width and height of the image.
     */
    public float getPixel(final int row, final int column) {
        return pixels[row][column];
    }

    /**
     * Sets the value of a pixel.
     * @param row Row of the pixel.
     * @param column Column of the pixel.
     * @param value Gray-scale value of the pixel (0 for black, 1 for white).
     * @throws IndexOutOfBoundsException if {@code row} or {@code column}
     * are negative or larger-equals the width and height of the image.
     */
    public void setPixel(final int row, final int column, final float value) {
        pixels[row][column] = value;
    }

    /**
     * Copies this image into a float array.
     * @return Two dimensional array in {@code {@code toArray()[row][column]}}
     * order. Use {@link #toArray(boolean)} for {@code toArray()[column][row]}
     * order.
     */
    public float[][] toArray() {
        return toArray(true);
    }

    /**
     * Copies this image into a float array.
     * @param firstIndexIsRow If {@code true} the elements of the array
     * are in {@code toArray()[row][column]} order, otherwise in
     * {@code toArray()[column][row]}. If {@code true} is passed, this
     * method behaves identically to {@link #toArray()}.
     * @return Two dimensional array with one element per pixel.
     */
    public float[][] toArray(final boolean firstIndexIsRow) {
        float[][] copy;
        if (firstIndexIsRow) {
            copy = new float[height][width];
            for (int row = 0; row < height; row++) {
                System.arraycopy(pixels[row], 0, copy[row], 0, width);
            }
        } else {
            copy = new float[width][height];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    copy[col][row] = pixels[row][col];
                }
            }
        }
        return copy;
    }

    /**
     * Copies this image into a {@link BufferedImage}.
     * @return BufferedImage with image type
     * {@link BufferedImage#TYPE_USHORT_GRAY}.
     */
    public BufferedImage toBufferedImage() {
        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_USHORT_GRAY);

        WritableRaster raster = bufferedImage.getRaster();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                raster.setSample(col, row, 0, pixels[row][col] * UFLOAT_MAX);
            }
        }

        return bufferedImage;
    }

    @Override
    public String toString() {
        return "Image(" + height + "," + width + ")";
    }
}
