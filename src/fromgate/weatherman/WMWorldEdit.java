/*  
 *  WeatherMan, Minecraft bukkit plugin
 *  (c)2012-2014, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/server-mods/weatherman/
 *    
 *  This file is part of WeatherMan.
 *  
 *  WeatherMan is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WeatherMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WeatherMan.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package fromgate.weatherman;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WMWorldEdit {
	
    private static WorldEditPlugin worldedit;
    private static WorldGuardPlugin worldguard;
    private static boolean worldedit_active=false;
    private static boolean worldguard_active=false;
    
    public static void init(){
        worldedit_active = ConnectWorldEdit();
        worldguard_active = ConnectWorldGuard();
    }
    
    
    public static boolean isWE(){
    	return worldedit_active;
    }
    
    public static boolean isWG(){
    	return worldguard_active;
    }

    
    public static boolean ConnectWorldEdit(){
        Plugin worldEdit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if ((worldEdit != null)&&(worldEdit instanceof WorldEditPlugin)) {
            worldedit = (WorldEditPlugin)worldEdit;
            return true;
        }
        return false;
    }

    public static boolean ConnectWorldGuard(){
        Plugin worldGuard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if ((worldGuard != null)&&(worldGuard instanceof WorldGuardPlugin)) {
            worldguard = (WorldGuardPlugin)worldGuard;
            return true;
        }
        return false;
    }
    
    public static Location getMinPoint(World world, String rg){
    	if (!worldguard_active) return null;
    	if (world == null) return null;
    	if (rg.isEmpty()) return null;
    	ProtectedRegion region = worldguard.getRegionManager(world).getRegion(rg);
    	if (region == null) return null;
    	return new Location (world,region.getMinimumPoint().getBlockX(),region.getMinimumPoint().getBlockY(),region.getMinimumPoint().getBlockZ());
    }

    public static Location getMaxPoint(World world, String rg){
    	if (!worldguard_active) return null;
    	if (world == null) return null;
    	if (rg.isEmpty()) return null;
    	ProtectedRegion region = worldguard.getRegionManager(world).getRegion(rg);
    	if (region == null) return null;
    	return new Location (world,region.getMaximumPoint().getBlockX(),region.getMaximumPoint().getBlockY(),region.getMaximumPoint().getBlockZ());
    }
    
    public static boolean isRegionExists(World world, String rg){
    	if (!WMWorldEdit.isWG()) return false;
    	if (world==null) return false;
    	if (rg.isEmpty()) return false;
    	ProtectedRegion region = worldguard.getRegionManager(world).getRegion(rg);
		return  (region != null);
    }
    
	public static boolean isRegionExists(String region){
		if (!WMWorldEdit.isWG()) return false;
		for (World w : Bukkit.getWorlds()){
			ProtectedRegion rg = worldguard.getRegionManager(w).getRegion(region);
			if (rg != null) return true;
		}
		return false;
	}
	
	public static List<String> getRegions(Location loc){
		List<String> rgList = new ArrayList<String>();
		if (!WMWorldEdit.isWG()) return rgList;
		ApplicableRegionSet rset = worldguard.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		if ((rset == null)||(rset.size()==0)) return rgList;
		for (ProtectedRegion rg : rset ) rgList.add(rg.getId());
		return rgList;
	}
    
    
    //WorldEdit
    public static boolean isSelected(Player player){
    	if (!worldedit_active) return false;
    	Selection sel = worldedit.getSelection(player);
    	return (sel!=null);
    }
    
    public static Location getSelectionMinPoint(Player player){
    	if (!worldedit_active) return null;
    	Selection sel = worldedit.getSelection(player);
    	if (sel==null) return null;
		return sel.getMinimumPoint();
    }
    
    public static Location getSelectionMaxPoint(Player player){
    	if (!worldedit_active) return null;
    	Selection sel = worldedit.getSelection(player);
    	if (sel==null) return null;
		return sel.getMaximumPoint();
    }

    
    
}
