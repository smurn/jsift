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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Three dimensional extension of an image using scale as the third dimension.
 */
public final class ScaleSpace {

    /** Octaves in this scale-space. */
    private final List<Octave> octaves;

    /**
     * Creates an instance.
     * @param octaves Octaves of this scale-space.
     * @throws NullPointerException if {@code octave} is {@code null}.
     * @throws IllegalArgumentException if there is not at least one octave.
     */
    public ScaleSpace(final List<Octave> octaves) {
        if (octaves == null) {
            throw new NullPointerException("octaves must not be null");
        }
        if (octaves.size() <= 0) {
            throw new IllegalArgumentException("need at least one octave");
        }
        this.octaves = Collections.unmodifiableList(
                new ArrayList<Octave>(octaves));
    }

    /**
     * Gets the octaves of this scale-space.
     * @return Immutable list of octaves.
     */
    public List<Octave> getOctaves() {
        return octaves;
    }
}
