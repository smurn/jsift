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

import java.awt.image.BufferedImage;

/**
 * Gray-scale image with 32-bit floating point precision.
 * A pixel value of 0 is interpreted as black, a value of 1 as white.
 */
public final class Image {

    /**
     * Creates an image of the given size wher all pixels have the value 0.0.
     * @param rows Number of rows of the image.
     * @param columns Number of rows of the image.
     * @throws IllegalArgumentException if {@code rows} or {@code cols} are
     * negative.
     */
    public Image(int rows, int columns) {
        throw new UnsupportedOperationException("not implemented");
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
    public Image(float[][] pixels) {
        throw new UnsupportedOperationException("not implemented");
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
    public Image(float[][] pixels, boolean firstIndexIsRow) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the value of a pixel.
     * @param row Row of the pixel.
     * @param column Column of the pixel.
     * @return Gray-scale value of the pixel (0 for black, 1 for white).
     * @throws IndexOutOfBoundsException if {@code row} or {@code column}
     * are negative or larger-equals the width and height of the image.
     */
    public float getPixel(int row, int column){
        throw new UnsupportedOperationException("not implemented");
    }
    
    /**
     * Sets the value of a pixel.
     * @param row Row of the pixel.
     * @param column Column of the pixel.
     * @param value Gray-scale value of the pixel (0 for black, 1 for white).
     * @throws IndexOutOfBoundsException if {@code row} or {@code column}
     * are negative or larger-equals the width and height of the image.
     */
    public void setPixel(int row, int column, float value){
        throw new UnsupportedOperationException("not implemented");
    }
    
    /**
     * Creates a copy from the given AWT image.
     * <p>If the given image is not a grayscale image it will be converted
     * using {@code 0.3*red + 0.59*green + 0.11*blue}.</p>
     * @param image Image to copy.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image(java.awt.Image image) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Copies this image into a float array.
     * @return Two dimensional array in {@code {@code toArray()[row][column]}}
     * order. Use {@link #toArray(boolean)} for {@code toArray()[column][row]}
     * order.
     */
    public float[][] toArray() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Copies this image into a float array.
     * @param firstIndexIsRow If {@code true} the elements of the array
     * are in {@code toArray()[row][column]} order, otherwise in
     * {@code toArray()[column][row]}. If {@code true} is passed, this
     * method behaves identically to {@link #toArray()}.
     * @return Two dimensional array with one element per pixel.
     */
    public float[][] toArray(boolean firstIndexIsRow) {
        throw new UnsupportedOperationException("not implemented");
    }
    
    /**
     * Copies this image into a {@link BufferedImage}.
     * @return BufferedImage with image type
     * {@link BufferedImage#TYPE_USHORT_GRAY}.
     */
    public BufferedImage toBufferedImage(){
        throw new UnsupportedOperationException("not implemented");
    }
}
