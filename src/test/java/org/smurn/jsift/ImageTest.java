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
import java.awt.image.WritableRaster;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link Image}.
 */
public class ImageTest {

    @Test(expected = IllegalArgumentException.class)
    public void ctrNegativeRows() {
        new Image(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrNegativeColumns() {
        new Image(10, -1);
    }

    @Test
    public void ctrZeroZero() {
        new Image(0, 0);
    }

    @Test
    public void ctrSizeSigma() {
        Image target = new Image(10, 10, 1.8, 2, 3, 4);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(2.0, target.getScale(), 1E-6);
        assertEquals(3.0, target.getOffsetX(), 1E-6);
        assertEquals(4.0, target.getOffsetY(), 1E-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrSizeSigmaZero() {
        new Image(10, 10, 0, 2, 3, 4);
    }

    @Test
    public void ctrFloat() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        Image target = new Image(input);
        assertArrayEquals(input, target.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrFloatSigmaZero() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        new Image(input, 0, 2, 3, 4);
    }

    @Test
    public void ctrFloatSigma() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        Image target = new Image(input, 1.8, 2, 3, 4);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(2.0, target.getScale(), 1E-6);
        assertEquals(3.0, target.getOffsetX(), 1E-6);
        assertEquals(4.0, target.getOffsetY(), 1E-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrFloatJagged() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5}};
        new Image(input);
    }

    @Test(expected = NullPointerException.class)
    public void ctrFloatNull() {
        new Image((float[][]) null);
    }

    @Test
    public void ctrFloatFalse() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        Image target = new Image(input, false);
        float[][] expected = new float[][]{{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(expected, target.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrFloatFalseSigmaZero() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        new Image(input, false, 0, 2, 3, 4);
    }

    @Test
    public void ctrFloatFalseSigma() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        Image target = new Image(input, false, 1.8, 2, 3, 4);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(2.0, target.getScale(), 1E-6);
        assertEquals(3.0, target.getOffsetX(), 1E-6);
        assertEquals(4.0, target.getOffsetY(), 1E-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrFloatFalseJagged() {
        float[][] input = new float[][]{{1, 4}, {2, 5, 10}, {3, 6}};
        new Image(input, false);
    }

    @Test(expected = NullPointerException.class)
    public void ctrFloatTrueNull() {
        new Image((float[][]) null, true);
    }

    @Test
    public void ctrFloatTrueZeroSize() {
        Image target = new Image(new float[0][], true);
        assertEquals(0, target.getWidth());
        assertEquals(0, target.getHeight());
    }

    @Test
    public void ctrFloatFalseZeroSize() {
        Image target = new Image(new float[0][], false);
        assertEquals(0, target.getWidth());
        assertEquals(0, target.getHeight());
    }

    @Test
    public void ctrImageTYPE_BYTE_GRAY() {
        BufferedImage bImage = new BufferedImage(3, 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bImage.getRaster();
        raster.setDataElements(0, 0, new byte[]{0});
        raster.setDataElements(1, 0, new byte[]{2});
        raster.setDataElements(2, 0, new byte[]{(byte) 255});

        Image target = new Image(bImage);
        float[][] expected = new float[][]{{0.0f, 2.0f / 255, 1.0f}};
        assertArrayEquals(expected, target.toArray());
    }

    @Test
    public void ctrImageTYPE_BYTE_GRAYSigma() {
        BufferedImage bImage = new BufferedImage(3, 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bImage.getRaster();
        raster.setDataElements(0, 0, new byte[]{0});
        raster.setDataElements(1, 0, new byte[]{2});
        raster.setDataElements(2, 0, new byte[]{(byte) 255});

        Image target = new Image(bImage, 1.8, 2, 3, 4);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(1.8, target.getSigma(), 1E-6);
        assertEquals(2.0, target.getScale(), 1E-6);
        assertEquals(3.0, target.getOffsetX(), 1E-6);
        assertEquals(4.0, target.getOffsetY(), 1E-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrImageTYPE_BYTE_GRAYSigmaZero() {
        BufferedImage bImage = new BufferedImage(3, 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bImage.getRaster();
        raster.setDataElements(0, 0, new byte[]{0});
        raster.setDataElements(1, 0, new byte[]{2});
        raster.setDataElements(2, 0, new byte[]{(byte) 255});
        new Image(bImage, 0, 2, 3, 4);
    }

    @Test(expected = NullPointerException.class)
    public void ctrImageNull() {
        new Image((java.awt.Image) null);
    }

    @Test
    public void copyCtr() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        Image image = new Image(input);
        Image copy = new Image(image);
        assertArrayEquals(input, copy.toArray());
    }

    @Test
    public void getWidth() {
        Image target = new Image(5, 10);
        assertEquals(10, target.getWidth());
    }

    @Test
    public void getHeight() {
        Image target = new Image(5, 10);
        assertEquals(5, target.getHeight());
    }

    @Test
    public void getPixel() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        Image target = new Image(input);
        assertEquals(4.0f, target.getPixel(1, 0), 1E-20);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getPixelRowNegative() {
        Image target = new Image(2, 2);
        target.getPixel(-1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getPixelColumnNegative() {
        Image target = new Image(2, 2);
        target.getPixel(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getPixelRowToLarge() {
        Image target = new Image(2, 2);
        target.getPixel(2, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getPixelColumnToLarge() {
        Image target = new Image(2, 2);
        target.getPixel(0, 2);
    }

    @Test
    public void setPixel() {
        Image target = new Image(2, 2);
        target.setPixel(1, 0, 0.5f);
        float[][] expected = new float[][]{{0, 0}, {0.5f, 0}};
        assertArrayEquals(expected, target.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setPixelRowNegative() {
        Image target = new Image(2, 2);
        target.setPixel(-1, 0, 0.5f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setPixelColumnNegative() {
        Image target = new Image(2, 2);
        target.setPixel(0, -1, 0.5f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setPixelRowToLarge() {
        Image target = new Image(2, 2);
        target.setPixel(2, 0, 0.5f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setPixelColumnToLarge() {
        Image target = new Image(2, 2);
        target.setPixel(0, 2, 0.5f);
    }

    @Test
    public void toArrayFalse() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        Image target = new Image(input);
        float[][] expected = new float[][]{{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(expected, target.toArray(false));
    }

    @Test
    public void toBufferedImage() {
        float[][] input = new float[][]{{0.1f, 0.4f}, {0.2f, 0.5f}, {0.3f, 0.6f}};
        Image target = new Image(input);
        BufferedImage actual = target.toBufferedImage();
        assertEquals(BufferedImage.TYPE_USHORT_GRAY, actual.getType());
        assertEquals(2, actual.getWidth());
        assertEquals(3, actual.getHeight());
        assertArrayEquals(new short[]{(short) (0.1f * 65535)}, (short[]) actual.getRaster().getDataElements(0, 0, null));
        assertArrayEquals(new short[]{(short) (0.2f * 65535)}, (short[]) actual.getRaster().getDataElements(0, 1, null));
        assertArrayEquals(new short[]{(short) (0.3f * 65535)}, (short[]) actual.getRaster().getDataElements(0, 2, null));
        assertArrayEquals(new short[]{(short) (0.4f * 65535)}, (short[]) actual.getRaster().getDataElements(1, 0, null));
        assertArrayEquals(new short[]{(short) (0.5f * 65535)}, (short[]) actual.getRaster().getDataElements(1, 1, null));
        assertArrayEquals(new short[]{(short) (0.6f * 65535)}, (short[]) actual.getRaster().getDataElements(1, 2, null));
    }

    @Test
    public void subtract() {
        Image minuend = new Image(new float[][]{{11, 14}, {12, 15}, {13, 16}});
        Image subtrahend = new Image(new float[][]{{1, 4}, {2, 5}, {3, 6}});
        Image expected = new Image(new float[][]{{10, 10}, {10, 10}, {10, 10}});

        Image actual = minuend.subtract(subtrahend);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void subtractPreservesTransformation() {
        Image minuend = new Image(10, 10, 10, 0.25, 0.2, 0.3);
        Image subtrahend = new Image(10, 10, 10, 0.25, 0.2, 0.3);
        Image actual = minuend.subtract(subtrahend);
        assertEquals(0.25, actual.getScale(), 1E-6);
        assertEquals(0.2, actual.getOffsetX(), 1E-6);
        assertEquals(0.3, actual.getOffsetY(), 1E-6);
    }

    @Test
    public void subtractSigma() {
        Image minuend = new Image(10, 10, 10, 1, 0, 0);
        Image subtrahend = new Image(10, 10, 20, 1, 0, 0);
        Image difference = minuend.subtract(subtrahend);
        double actual = difference.getSigma();
        double expected = Math.exp(0.5 * (Math.log(10) + Math.log(20)));
        assertEquals(expected, actual, 1E-6);
    }

    @Test(expected = NullPointerException.class)
    public void subtractNull() {
        Image minuend = new Image(new float[][]{{11, 14}, {12, 15}, {13, 16}});
        minuend.subtract(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractWrongDimensions() {
        Image minuend = new Image(new float[][]{{11, 14}, {12, 15}, {13, 16}});
        Image subtrahend = new Image(new float[][]{{1, 4}, {2, 5}});
        minuend.subtract(subtrahend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractDifferentScale() {
        Image minuend = new Image(10, 10, 10, 1.0, 0.0, 0.0);
        Image subtrahend = new Image(10, 10, 20, 1.1, 0.0, 0.0);
        minuend.subtract(subtrahend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractDifferentOffsetX() {
        Image minuend = new Image(10, 10, 10, 1.0, 0.5, 0.0);
        Image subtrahend = new Image(10, 10, 20, 1.0, 0.0, 0.0);
        minuend.subtract(subtrahend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractDifferentOffsetY() {
        Image minuend = new Image(10, 10, 10, 1.0, 0.0, 0.5);
        Image subtrahend = new Image(10, 10, 20, 1.0, 0.0, 0.0);
        minuend.subtract(subtrahend);
    }
}
