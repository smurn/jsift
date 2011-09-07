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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.smurn.jsift.TestUtils.*;

/**
 * Unit tests for {@link Subsampler}.
 */
public class SubsamplerTest {

    @Test(expected = NullPointerException.class)
    public void imageNull() {
        Subsampler target = new Subsampler();
        target.downScale(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void image0x0() {
        Image image = new Image(0, 0);
        Subsampler target = new Subsampler();
        target.downScale(image);
    }

    @Test
    public void image1x1() {
        Image image = new Image(new float[][]{{0.3f}});
        Subsampler target = new Subsampler();
        Image actual = target.downScale(image);
        assertThat(actual, equalTo(image, 1E-5f));
    }

    @Test
    public void image2x2() {
        Image image = new Image(new float[][]{{0.3f, 0.4f}, {0.5f, 0.6f}});
        Image expected = new Image(new float[][]{{0.3f}});
        Subsampler target = new Subsampler();
        Image actual = target.downScale(image);
        assertThat(actual, equalTo(expected, 1E-5f));
    }

    @Test
    public void image3x2() {
        Image image = new Image(new float[][]{{0.3f, 0.4f}, {0.5f, 0.6f}, {0.7f, 0.8f}});
        Image expected = new Image(new float[][]{{0.3f}, {0.7f}});
        Subsampler target = new Subsampler();
        Image actual = target.downScale(image);
        assertThat(actual, equalTo(expected, 1E-5f));
    }

    @Test
    public void image3x3() {
        Image image = new Image(new float[][]{{0.3f, 0.4f, -0.1f}, {0.5f, 0.6f, -0.2f}, {0.7f, 0.8f, -0.3f}});
        Image expected = new Image(new float[][]{{0.3f, -0.1f}, {0.7f, -0.3f}});
        Subsampler target = new Subsampler();
        Image actual = target.downScale(image);
        assertThat(actual, equalTo(expected, 1E-5f));
    }

    @Test
    public void sigma() {
        Subsampler target = new Subsampler();
        Image actual = target.downScale(new Image(10, 10, 3.2, 2, 3, 4));
        assertEquals(3.2, actual.getSigma(), 1E-6);
        assertEquals(1.0, actual.getScale(), 1E-6);
        assertEquals(1.5, actual.getOffsetX(), 1E-6);
        assertEquals(2.0, actual.getOffsetY(), 1E-6);
    }
}
