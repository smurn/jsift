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

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ScaleSpaceFactoryImpl}.
 */
public class ScaleSpaceFactoryImplTest {

    ScaleSpaceFactoryImpl target;
    DownScaler downScaler;
    UpScaler upScaler;
    LowPassFilter filter;
    OctaveFactory octaveFactory;

    @Before
    public void setUp() {
        target = new ScaleSpaceFactoryImpl();
        downScaler = mock(DownScaler.class);
        when(downScaler.downScale(any(Image.class))).thenAnswer(new Answer<Image>() {

            @Override
            public Image answer(InvocationOnMock invocation) throws Throwable {
                Image image = (Image) invocation.getArguments()[0];
                return new Image(image.getHeight() - 1, image.getWidth() - 1, image.getSigma(), image.getScale() / 2, 0, 0);
            }
        });

        upScaler = mock(UpScaler.class);
        when(upScaler.upScale(any(Image.class))).thenAnswer(new Answer<Image>() {

            @Override
            public Image answer(InvocationOnMock invocation) throws Throwable {
                Image image = (Image) invocation.getArguments()[0];
                return new Image(image.getHeight() + 1, image.getWidth() + 1, image.getSigma(), image.getScale() * 2, 0, 0);
            }
        });

        filter = mock(LowPassFilter.class);
        when(filter.filter(any(Image.class), anyDouble())).thenAnswer(new Answer<Image>() {

            @Override
            public Image answer(InvocationOnMock invocation) throws Throwable {
                Image image = (Image) invocation.getArguments()[0];
                double sigma = (Double) invocation.getArguments()[1];
                return new Image(image.getHeight(), image.getWidth(), sigma, image.getScale(), 0, 0);
            }
        });

        octaveFactory = new OctaveFactoryImpl();

    }

    @Test(expected = NullPointerException.class)
    public void imageNull() {
        target.create(null, 3, 1.7,
                mock(UpScaler.class), mock(DownScaler.class), mock(LowPassFilter.class), mock(OctaveFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void upscalerNull() {
        target.create(new Image(10, 10), 3, 1.7,
                null, mock(DownScaler.class), mock(LowPassFilter.class), mock(OctaveFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void downscalerNull() {
        target.create(new Image(10, 10), 3, 1.7,
                mock(UpScaler.class), null, mock(LowPassFilter.class), mock(OctaveFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void filterNull() {
        target.create(new Image(10, 10), 3, 1.7,
                mock(UpScaler.class), mock(DownScaler.class), null, mock(OctaveFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void octaveFactoryNull() {
        target.create(new Image(10, 10), 3, 1.7,
                mock(UpScaler.class), mock(DownScaler.class), mock(LowPassFilter.class), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scalesPerOctaveZero() {
        target.create(new Image(10, 10), 0, 1.7,
                mock(UpScaler.class), mock(DownScaler.class), mock(LowPassFilter.class), mock(OctaveFactory.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toSmallInitialSigma() {
        target.create(new Image(10, 10), 3, 0.49,
                mock(UpScaler.class), mock(DownScaler.class), mock(LowPassFilter.class), mock(OctaveFactory.class));
    }

    @Test
    public void smallImage() {
        ScaleSpace scaleSpace = target.create(new Image(10, 1), 1, 1.7, 
                upScaler, downScaler, filter, octaveFactory);
        assertEquals(2, scaleSpace.getOctaves().size());

        assertEquals(2, scaleSpace.getOctaves().get(0).getWidth());
        assertEquals(11, scaleSpace.getOctaves().get(0).getHeight());

        assertEquals(1, scaleSpace.getOctaves().get(1).getWidth());
        assertEquals(10, scaleSpace.getOctaves().get(1).getHeight());
    }

    @Test
    public void octave0scale0() {
        ScaleSpace scaleSpace = target.create(new Image(10, 10), 1, 1.7, 
                upScaler, downScaler, filter, octaveFactory);
        Image firstImage = scaleSpace.getOctaves().get(0).getScaleImages().get(0);
        assertEquals(1.7, firstImage.getSigma(), 1E-6);
    }

    @Test
    public void octave1scale0() {
        ScaleSpace scaleSpace = target.create(new Image(10, 10), 1, 1.7, 
                upScaler, downScaler, filter, octaveFactory);
        Image firstImage = scaleSpace.getOctaves().get(1).getScaleImages().get(0);
        assertEquals(2 * 1.7, firstImage.getSigma(), 1E-6);
    }

    @Test
    public void octave2scale0() {
        ScaleSpace scaleSpace = target.create(new Image(10, 10), 1, 1.7, 
                upScaler, downScaler, filter, octaveFactory);
        Image firstImage = scaleSpace.getOctaves().get(2).getScaleImages().get(0);
        assertEquals(4 * 1.7, firstImage.getSigma(), 1E-6);
    }
}
