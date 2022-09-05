package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("bpr")
public class bluePrintsRedundance implements bluePrintsFilters{
    @Override
    public Blueprint filtrar(Blueprint blueprint) {
    List<Point> flatpoint = blueprint.getPoints();
    List<Point> puntosfil = new ArrayList<Point>();
    for(int i=0;i<flatpoint.size();i++){
        System.out.println();
        puntosfil.add(flatpoint.get(i));
        for(int j=0;j<puntosfil.size();j++){
            if(flatpoint.get(i).equals(puntosfil.get(j))){
                puntosfil.remove(i);
            }
        }

    }
    return new Blueprint(blueprint.getAuthor(), blueprint.getName(),puntosfil);
    }
}
