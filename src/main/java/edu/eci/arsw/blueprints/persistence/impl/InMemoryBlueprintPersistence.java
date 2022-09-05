/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Component("bpp")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        if (!blueprints.containsKey(new Tuple<>(author,bprintname))){
            throw new BlueprintNotFoundException("Blueprint not found");
        }
        else{
           return blueprints.get(new Tuple<>(author,bprintname));
        }
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
       Set<Blueprint> blueprintsByAuthor = blueprints.entrySet().stream().filter(e -> author.equals(e.getKey().getElem1())).map(Map.Entry::getValue).collect(Collectors.toSet());
       
       if(blueprintsByAuthor != null){
           return blueprintsByAuthor;
       }else{
           throw new BlueprintNotFoundException("This author doesn´t exist");
       }
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return blueprints.values().stream().collect(Collectors.toSet());
    }

    
    
}
