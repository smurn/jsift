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
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OctaveFactoryImpl}.
 */
public class OctaveFactoryImplTest {

    private static class MockFilter implements LowPassFilter {

        @Override
        public Image filter(Image image, double sigma) {
            return new Image(10, 10, sigma, 2, 3, 4);
        }
    }
    private OctaveFactoryImpl target;

    @Before
    public void setUp() {
        target = new OctaveFactoryImpl();
    }

    @Test(expected = NullPointerException.class)
    public void createImageNull() {
        target.create(null, 3, mock(LowPassFilter.class));
    }

    @Test(expected = NullPointerException.class)
    public void createFilterNull() {
        target.create(new Image(10, 10), 3, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createZeroScales() {
        target.create(new Image(10, 10), 0, mock(LowPassFilter.class));
    }


    @Test
    public void createScaleImages1() {
        LowPassFilter filter = new MockFilter();
        Octave octave = target.create(new Image(10, 10, 1.5, 2, 3, 4), 1, filter);
        assertEquals(1, octave.getScalesPerOctave());
        assertEquals(1.5, (octave.getScaleImages().get(0)).getSigma(), 1E-6);
        assertEquals(3.0, (octave.getScaleImages().get(1)).getSigma(), 1E-6);
        assertEquals(6.0, (octave.getScaleImages().get(2)).getSigma(), 1E-6);
        assertEquals(12.0, (octave.getScaleImages().get(3)).getSigma(), 1E-6);
    }

    @Test
    public void createScaleImages3() {
        LowPassFilter filter = new MockFilter();
        Octave octave = target.create(new Image(10, 10, 1.5, 2, 3, 4), 3, filter);
        assertEquals(3, octave.getScalesPerOctave());
        assertEquals(1.5 * Math.pow(2, 0.0 / 3.0), (octave.getScaleImages().get(0)).getSigma(), 1E-6);
        assertEquals(1.5 * Math.pow(2, 1.0 / 3.0), (octave.getScaleImages().get(1)).getSigma(), 1E-6);
        assertEquals(1.5 * Math.pow(2, 2.0 / 3.0), (octave.getScaleImages().get(2)).getSigma(), 1E-6);
        assertEquals(1.5 * Math.pow(2, 3.0 / 3.0), (octave.getScaleImages().get(3)).getSigma(), 1E-6);
        assertEquals(1.5 * Math.pow(2, 4.0 / 3.0), (octave.getScaleImages().get(4)).getSigma(), 1E-6);
        assertEquals(1.5 * Math.pow(2, 5.0 / 3.0), (octave.getScaleImages().get(5)).getSigma(), 1E-6);
    }

    @Test
    public void createDoG1() {
        LowPassFilter filter = new MockFilter();
        Octave octave = target.create(new Image(10, 10, 1.5, 2, 3, 4), 1, filter);
        assertEquals(1, octave.getScalesPerOctave());
        assertEquals(logMean(1.5, 3.0), (octave.getDifferenceOfGaussians().get(0)).getSigma(), 1E-6);
        assertEquals(logMean(3.0, 6.0), (octave.getDifferenceOfGaussians().get(1)).getSigma(), 1E-6);
        assertEquals(logMean(6.0, 12.0), (octave.getDifferenceOfGaussians().get(2)).getSigma(), 1E-6);
    }

    @Test
    public void createDoG3() {
        LowPassFilter filter = new MockFilter();
        Octave octave = target.create(new Image(10, 10, 1.5, 2, 3, 4), 3, filter);
        assertEquals(3, octave.getScalesPerOctave());
        assertEquals(logMean(1.5 * Math.pow(2, 0.0 / 3.0), 1.5 * Math.pow(2, 1.0 / 3.0)),
                (octave.getDifferenceOfGaussians().get(0)).getSigma(), 1E-6);
        assertEquals(logMean(1.5 * Math.pow(2, 1.0 / 3.0), 1.5 * Math.pow(2, 2.0 / 3.0)),
                (octave.getDifferenceOfGaussians().get(1)).getSigma(), 1E-6);
        assertEquals(logMean(1.5 * Math.pow(2, 2.0 / 3.0), 1.5 * Math.pow(2, 3.0 / 3.0)),
                (octave.getDifferenceOfGaussians().get(2)).getSigma(), 1E-6);
        assertEquals(logMean(1.5 * Math.pow(2, 3.0 / 3.0), 1.5 * Math.pow(2, 4.0 / 3.0)),
                (octave.getDifferenceOfGaussians().get(3)).getSigma(), 1E-6);
        assertEquals(logMean(1.5 * Math.pow(2, 4.0 / 3.0), 1.5 * Math.pow(2, 5.0 / 3.0)),
                (octave.getDifferenceOfGaussians().get(4)).getSigma(), 1E-6);
    }

    private double logMean(double a, double b) {
        return Math.exp(0.5 * (Math.log(a) + Math.log(b)));
    }
}
