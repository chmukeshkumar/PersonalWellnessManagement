/* 
 * Copyright (C) 2016 mchippa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pwm.mdp.solver;

import java.util.List;

/**
 *
 * @author mchippa
 */
public abstract class Action {

    /**
     *
     */
    protected String name; 
    
    /**
     *
     * @param name
     */
    public Action(String name) {
        this.name = name;
    }
    
    /**
     *
     * @return
     */
    public String getName() { 
        return this.name;
    }
    
    /**
     *
     * @param s
     * @return
     */
    public final boolean applicableInState(State s) {
        return true;
    }
    
    /**
     *
     * @param s
     * @return
     */
    public abstract List<TransitionProbability> getTransitions(State s);
    
    /**
     *
     * @param s
     * @return
     */
    public boolean isApplicableInState(State s) {
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public boolean equals(Object o ) {
        return this.name.equals(o);
    }
    
}
