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
package pwm.mdp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mchippa
 */
public class PWMStateSpace {
    Map<String, PWMState> stateSpace = new HashMap<String,PWMState> ();
    
    Map generateStateSpace(int initialWeight, int finalWeight, int stepSize) {
        for (int w = finalWeight; w<= initialWeight ; w+=stepSize ) {
            stateSpace.put(""+w, new PWMState(w));
        }
        return stateSpace;
    }
    
    Map<String,PWMState> getStateSpace() {
        return this.stateSpace;
    }
    
    
}
