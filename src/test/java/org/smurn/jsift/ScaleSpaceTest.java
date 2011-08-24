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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.smurn.jsift.TestUtils.*;

/**
 * Unit tests for {@link ScaleSpace}.
 */
public class ScaleSpaceTest {

    private Image image;
    private Image image2;
    private UpScaler upScaler = new LinearUpScalerImpl();
    private DownScaler downScaler = new SubsamplerImpl();
    private LowPassFilter lowFilter = new GaussianFilterImpl();

    @Before
    public void setup() {
        image = new Image(20, 20);
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                image.setPixel(row, col, (row * col) / (20.0f * 20.0f));
            }
        }
        LinearUpScalerImpl interpolator = new LinearUpScalerImpl();
        image2 = interpolator.upScale(image);
    }

    @Test(expected = NullPointerException.class)
    public void nullImage() {
        new ScaleSpace(null);
    }

    @Test(expected = NullPointerException.class)
    public void toSmallImage() {
        new ScaleSpace(new Image(1, 2));
    }

    @Test
    public void minimalSizeImage() {
        new ScaleSpace(new Image(2, 2));
    }

    @Test
    public void sizeIncreased() {
        ScaleSpace target = new ScaleSpace(new Image(10, 10));
        Image actual = target.getScaleLevel(0).getGaussian0();
        assertThat(actual.getWidth(), is(20));
        assertThat(actual.getHeight(), is(20));
    }

    @Test
    public void initialBlurring() {
        GaussianFilterImpl filter = new GaussianFilterImpl();
        Image expected = filter.filter(image2, Math.sqrt(2.1 * 2.1 - 0.8 * 0.8));

        ScaleSpace target = new ScaleSpace(image, 4, 0.4, 2.1, upScaler, downScaler, lowFilter);
        Image actual = target.getScaleLevel(0).getGaussian0();

        assertThat(actual, equalTo(expected, 1E-5f));
    }

    @Test
    public void gaussian2() {
        double sigma = 2 * Math.pow(2, 0.25);
        GaussianFilterImpl filter = new GaussianFilterImpl();
        Image expected = filter.filter(image2, Math.sqrt(sigma * sigma - 0.8 * 0.8));

        ScaleSpace target = new ScaleSpace(image, 4, 0.4, 2.0, upScaler, downScaler, lowFilter);
        Image actual = target.getScaleLevel(0).getGaussian2();

        assertThat(actual, equalTo(expected, 1E-5f));
    }
    
    @Test
    public void gaussian4() {
        double sigma = 2 * Math.pow(2, 0.5);
        GaussianFilterImpl filter = new GaussianFilterImpl();
        Image expected = filter.filter(image2, Math.sqrt(sigma * sigma - 0.8 * 0.8));

        ScaleSpace target = new ScaleSpace(image, 4, 0.4, 2.0, upScaler, downScaler, lowFilter);
        Image actual = target.getScaleLevel(0).getGaussian4();

        assertThat(actual, equalTo(expected, 1E-5f));
    }
}
