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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Gray-scale image with 32-bit floating point precision.
 * <p>A pixel value of 0 is interpreted as black, a value of 1 as white. But
 * the value range is not restricted to this range.</p>
 * <p>The sigma value describes the 'blurriness' of the image. The
 * higher the value the less detail is contained in the image.
 * <br/>Sigma is always larger than 0 and doubling sigma adds sufficient blur
 * to down-scale without significant information loss. Constructors
 * that do not specifify the sigma explicity will set it to 0.5 as proposed
 * by Lowe (with the exception of the copy-constructor of course).<br/>
 * The sigma value is always in reference to the original image. Operations such
 * as up-scale and down-scale do NOT change the sigma value, since they do
 * not change the amount of details recognisable in the image (we assume
 * that the image was sufficently low-pass filtered before it is down-scaled).
 * <br/>
 * Another way to look at it is as the inverse to the information content.
 * The higher the sigma the less information is contained in the image. This
 * is (to some degree) independent of the width / height of the image since
 * just increasing the number of pixels does not introduce new information.
 * <br/>In pratical terms sigma is related to the width of the gaussian kernel
 * that needs to applied to the original image to produce an image with the same
 * information content (note that the original image already has a >0 sigma). 
 * But since the filter method is replacable one should not depend on this
 * definition.</p>
 * <p>Each image stores the necessary information to transform from
 * pixel cooridnates in this image to the coordinates in the original
 * image that was provided to this library by the application code. The
 * transformation formulas are {@code x'=x*scale+offsetX} and 
 * {@code y'=y*scale+offsetY}, where {@code x,y} are
 * pixel-centric coordinates in the original image and {@code x',y'} are
 * pixel-centric coordinates in this image.</p>
 */
public class Image {

    private final float[][] pixels;
    private final int height;
    private final int width;
    private final double sigma;
    private final double scale;
    private final double offsetX;
    private final double offsetY;
    private static final float UFLOAT_MAX = 65535.0f;
    private static final double DEFAULT_SIGMA = 0.5;

    /**
     * Creates an image of the given size where all pixels have the value zero.
     * @param height Number of rows of the image.
     * @param width Number of rows of the image.
     * @param sigma Blurriness of this image relative to the original image.
     * @param scale Scale parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetX Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetY Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @throws IllegalArgumentException if {@code rows} or {@code cols} are
     * negative or if sigma is not strictly postitive.
     */
    public Image(final int height, final int width, final double sigma,
            final double scale, final double offsetX, final double offsetY) {
        if (height < 0) {
            throw new IllegalArgumentException("Cannot create image"
                    + " with " + height + " rows.");
        }
        if (width < 0) {
            throw new IllegalArgumentException("Cannot create image"
                    + " with " + height + " columns.");
        }
        if (sigma <= 0) {
            throw new IllegalArgumentException(
                    "sigma must be larger than zero");
        }
        this.pixels = new float[height][width];
        this.height = height;
        this.width = width;
        this.sigma = sigma;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * Creates an image of the given size where all pixels have the value zero.
     * @param height Number of rows of the image.
     * @param width Number of rows of the image.
     * @throws IllegalArgumentException if {@code rows} or {@code cols} are
     * negative.
     */
    public Image(final int height, final int width) {
        this(height, width, DEFAULT_SIGMA, 1.0, 0.0, 0.0);
    }

    /**
     * Creates an image from the given pixel values.
     * @param pixels Two dimensional array containing the pixels. The array
     * elements are interpreted as {@code pixels[row][column]}. Use
     * {@link #Image(float[][], boolean)} to read images in
     * {@code pixels[row][column]} order.
     * @param sigma Blurriness of this image relative to the original image.
     * @param scale Scale parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetX Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetY Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @throws NullPointerException if {@code pixels} or one of the sub-arrays
     * is {@code null}.
     * @throws IllegalArgumentException if the sub-arrays do not all have
     * equal length or if sigma is not strictly postitive.
     */
    public Image(final float[][] pixels, final double sigma, final double scale,
            final double offsetX, final double offsetY) {
        this(pixels, true, sigma, scale, offsetX, offsetY);
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
        this(pixels, true, DEFAULT_SIGMA, 1.0, 0.0, 0.0);
    }

    /**
     * Creates an image from the given pixel values.
     * @param pixels Two dimensional array containing the pixels.
     * @param firstIndexIsRow If {@code true} the elements of the array
     * are interpreted as {@code pixels[row][column]}, otherwise as
     * {@code pixels[column][row]}. If {@code true} is passed, this
     * method behaves identically to {@link #Image(float[][])}.
     * @param sigma Blurriness of this image relative to the original image.
     * @param scale Scale parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetX Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetY Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @throws NullPointerException if {@code pixels} or one of the sub-arrays
     * is {@code null}.
     * @throws IllegalArgumentException if the sub-arrays do not all have
     * equal length or if sigma is not strictly postitive.
     */
    public Image(final float[][] pixels, final boolean firstIndexIsRow,
            final double sigma, final double scale, final double offsetX,
            final double offsetY) {
        if (pixels == null) {
            throw new NullPointerException("pixels cannot be null.");
        }
        if (sigma <= 0) {
            throw new IllegalArgumentException(
                    "sigma must be larger than zero");
        }
        this.sigma = sigma;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

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
        this(pixels, firstIndexIsRow, DEFAULT_SIGMA, 1.0, 0.0, 0.0);
    }

    /**
     * Creates a copy from the given AWT image.
     * <p>If the given image is not a grayscale image it will be converted.</p>
     * @param image Image to copy.
     * @param sigma Blurriness of this image relative to the original image.
     * @param scale Scale parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetX Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @param offsetY Offset parameter of the coordinate transformation
     * (see {@link Image}).
     * @throws NullPointerException if {@code image} is {@code null}.
     * @throws IllegalArgumentException if sigma is not strictly positive.
     */
    public Image(final java.awt.Image image, final double sigma,
            final double scale, final double offsetX, final double offsetY) {
        if (sigma <= 0) {
            throw new IllegalArgumentException(
                    "sigma must be larger than zero");
        }
        if (image == null) {
            throw new NullPointerException("image must not be null");
        }
        this.sigma = sigma;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

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
     * Creates a copy from the given AWT image.
     * <p>If the given image is not a grayscale image it will be converted.</p>
     * @param image Image to copy.
     * @throws NullPointerException if {@code image} is {@code null}.
     */
    public Image(final java.awt.Image image) {
        this(image, DEFAULT_SIGMA, 1.0, 0.0, 0.0);
    }

    /**
     * Copy constructor.
     * @param image Image to copy.
     */
    public Image(final Image image) {
        this(image.pixels, image.sigma, image.scale, image.offsetX,
                image.offsetY);
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
     * Gets the blurriness of this image.
     * The image's sigma is relative to the original image. Therefore
     * up- and down-scaling does not affect the sigma value.
     * @return Sigma of this image.
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Gets the scale of the coordinate transformation.
     * See {@link Image}.
     * @return Scale of the coordinate transformation.
     */
    public double getScale() {
        return scale;
    }

    /**
     * Gets the offset of the coordinate transformation.
     * See {@link Image}.
     * @return Offset of the coordinate transformation.
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * Gets the offset of the coordinate transformation.
     * See {@link Image}.
     * @return Offset of the coordinate transformation.
     */
    public double getOffsetY() {
        return offsetY;
    }
    
    /**
     * Transforms a pixel-centric point of this image
     * to the correspoding pixel-centric point in the original image.
     * @param point Point in this image.
     * @return Point in the original image.
     */
    public Point2D.Double toOriginal(Point2D point){
        return new Point2D.Double(
                (point.getX() - offsetX) / scale,
                (point.getY() - offsetY) / scale);
    }
    
    /**
     * Transforms a pixel-centric point of the original image
     * to the correspoding pixel-centric point in this image.
     * @param point Point in the original image.
     * @return Point in this image.
     */
    public Point2D.Double fromOriginal(Point2D point){
        return new Point2D.Double(
                point.getX() * scale + offsetX,
                point.getY() * scale + offsetY);
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
     * Pixel-wise subtraction.
     * @param subtrahend Image to subtract from this image.
     * @return Difference of the two images.
     * @throws NullPointerException if {@code subtrahend} is {@code null}.
     * @throws IllegalArgumentException if the image has a different dimension
     * than this image or if the images have a transformation from the
     * original image.
     */
    public Image subtract(final Image subtrahend) {
        if (subtrahend == null) {
            throw new NullPointerException("subtrahend must not be null");
        }

        if (subtrahend.getWidth() != width
                || subtrahend.getHeight() != height) {
            throw new IllegalArgumentException(
                    "images have different dimensions.");
        }

        if (subtrahend.getScale() != scale
                || subtrahend.getOffsetX() != offsetX
                || subtrahend.getOffsetY() != offsetY) {
            throw new IllegalArgumentException(
                    "images have different transformations from the original "
                    + "image.");
        }

        double meanSigma = Math.exp(
                (Math.log(sigma) + Math.log(subtrahend.sigma)) / 2.0);
        Image difference = new Image(height, width, meanSigma,
                scale, offsetX, offsetY);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                float d = getPixel(row, col)
                        - subtrahend.getPixel(row, col);
                difference.setPixel(row, col, d);
            }
        }
        return difference;
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
        return "Image(w=" + height + " h=" + width + " s=" + sigma + " scale="
                + scale + " oX=" + offsetX + " oY=" + offsetY + ")";
    }
}
