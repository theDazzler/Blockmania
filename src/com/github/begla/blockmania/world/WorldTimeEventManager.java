/*
 * Copyright 2011 Benjamin Glatzel <benjamin.glatzel@me.com>.
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
package com.github.begla.blockmania.world;

import javolution.util.FastList;

/**
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class WorldTimeEventManager {

    protected FastList<WorldTimeEvent> _worldTimeEvents = new FastList<WorldTimeEvent>();
    protected WorldProvider _parent;

    public WorldTimeEventManager(WorldProvider parent) {
        _parent = parent;
    }

    /**
     * Adds a time event to the list.
     *
     * @param e
     */
    public void addWorldTimeEvent(WorldTimeEvent e) {
        _worldTimeEvents.add(e);
    }

    /**
     * Removes a time event from the list.
     *
     * @param e
     */
    public void removeWorldTimeEvent(WorldTimeEvent e) {
        _worldTimeEvents.remove(e);
    }

    /**
     * Executes all time events which event times equal a specified delta value.
     */
    public void fireWorldTimeEvents() {
        for (int i = _worldTimeEvents.size() - 1; i >= 0; i--) {
            final WorldTimeEvent event = _worldTimeEvents.get(i);

            if (event.getExecutionTime() > _parent.getTime() % 1.0)
                event.setCanFire(true);

            if (event.getExecutionTime() <= _parent.getTime() % 1.0 && event.canFire()) {
                event.setCanFire(false);
                event.execute();
            }

            if (!event.isRepeatingEvent())
                _worldTimeEvents.remove(i);
        }
    }

}
