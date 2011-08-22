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
    public void ctrFloat() {
        float[][] input = new float[][]{{1, 2, 3}, {4, 5, 6}};
        Image target = new Image(input);
        assertArrayEquals(input, target.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void ctrFloatNull() {
        new Image((float[][]) null);
    }

    @Test
    public void ctrFloatTrue() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        Image target = new Image(input, true);
        float[][] expected = new float[][]{{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(expected, target.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void ctrFloatTrueNull() {
        new Image((float[][]) null, true);
    }

    @Test
    public void ctrImageTYPE_BYTE_GRAY() {
        BufferedImage bImage = new BufferedImage(3, 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bImage.getRaster();
        raster.setDataElements(0, 0, new byte[]{0});
        raster.setDataElements(1, 0, new byte[]{20});
        raster.setDataElements(2, 0, new byte[]{(byte) 255});

        Image target = new Image(bImage);
        float[][] expected = new float[][]{{0.0f, 2.0f / 255, 1.0f}};
        assertArrayEquals(expected, target.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void ctrImageNull() {
        new Image((java.awt.Image) null);
    }

    @Test
    public void getWidth() {
        Image target = new Image(5, 10);
        assertEquals(5, target.getWidth());
    }

    @Test
    public void getHeight() {
        Image target = new Image(5, 10);
        assertEquals(10, target.getHeight());
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
    public void toArrayTrue() {
        float[][] input = new float[][]{{1, 4}, {2, 5}, {3, 6}};
        Image target = new Image(input);
        float[][] expected = new float[][]{{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(expected, target.toArray(true));
    }

    @Test
    public void toBufferedImage() {
        float[][] input = new float[][]{{0.1f, 0.4f}, {0.2f, 0.5f}, {0.3f, 0.6f}};
        Image target = new Image(input);
        BufferedImage actual = target.toBufferedImage();
        assertEquals(BufferedImage.TYPE_USHORT_GRAY, actual.getType());
        assertEquals(2, actual.getWidth());
        assertEquals(3, actual.getHeight());
        assertArrayEquals(new short[]{(short) ( 0.1f * 65536 )}, (short[]) actual.getRaster().getDataElements(0, 0, null));
        assertArrayEquals(new short[]{(short) ( 0.2f * 65536 )}, (short[]) actual.getRaster().getDataElements(0, 1, null));
        assertArrayEquals(new short[]{(short) ( 0.3f * 65536 )}, (short[]) actual.getRaster().getDataElements(0, 2, null));
        assertArrayEquals(new short[]{(short) ( 0.4f * 65536 )}, (short[]) actual.getRaster().getDataElements(1, 0, null));
        assertArrayEquals(new short[]{(short) ( 0.5f * 65536 )}, (short[]) actual.getRaster().getDataElements(1, 1, null));
        assertArrayEquals(new short[]{(short) ( 0.6f * 65536 )}, (short[]) actual.getRaster().getDataElements(1, 2, null));
    }
}
