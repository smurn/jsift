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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Helper method for test classes.
 */
public final class TestUtils {

    /**
     * Class cannot be instantiated.
     */
    private TestUtils() {
        // empty
    }

    private static class ImageMatcher extends BaseMatcher<Image> {

        private final Image image;
        private final float epsilon;

        public ImageMatcher(Image image, float epsilon) {
            if (image == null) {
                throw new NullPointerException();
            }
            if (epsilon < 0) {
                throw new IllegalArgumentException();
            }
            this.image = image;
            this.epsilon = epsilon;
        }

        @Override
        public boolean matches(Object item) {
            if (!(item instanceof Image)) {
                return false;
            }
            Image other = (Image) item;
            if (image.getWidth() != other.getWidth()) {
                return false;
            }
            if (image.getHeight() != other.getHeight()) {
                return false;
            }
            double maxError = 0;
            for (int row = 0; row < image.getHeight(); row++) {
                for (int col = 0; col < image.getWidth(); col++) {
                    float mePixel = image.getPixel(row, col);
                    float otherPixel = other.getPixel(row, col);
                    float error = Math.abs(mePixel - otherPixel);
                    maxError = Math.max(maxError, error);
                }
            }
            if (maxError > epsilon) {
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("similar to ").appendValue(image);
        }
    }

    public static Matcher<Image> equalTo(Image image, float epsilon) {
        return new ImageMatcher(image, epsilon);
    }
}
