/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Zebas
 */
public class Reduce {
    public static void main(String[] args){
        /*
        String[] acctnce = {"q6", "q5"};
        HashMap<String,ArrayList<String>> map = new HashMap<>();
        
        String[] tran = {"q7", "q1"};
        ArrayList<String> trans = new ArrayList<>(Arrays.asList(tran));
        map.put("q0", trans);
        
        String[] tran2 = {"q7", "q0"};
        ArrayList<String> trans2 = new ArrayList<>(Arrays.asList(tran2));
        map.put("q1", trans2);
        
        String[] tran3 = {"q4", "q5"};
        ArrayList<String> trans3 = new ArrayList<>(Arrays.asList(tran3));
        map.put("q2", trans3);
        
        String[] tran4 = {"q4", "q5"};
        ArrayList<String> trans4 = new ArrayList<>(Arrays.asList(tran4));
        map.put("q3", trans4);
        
        String[] tran5 = {"q6", "q6"};
        ArrayList<String> trans5 = new ArrayList<>(Arrays.asList(tran5));
        map.put("q4", trans5);
        
        String[] tran6 = {"q5", "q5"};
        ArrayList<String> trans6 = new ArrayList<>(Arrays.asList(tran6));
        map.put("q5", trans6);
        
        String[] tran7 = {"q5", "q5"};
        ArrayList<String> trans7 = new ArrayList<>(Arrays.asList(tran7));
        map.put("q6", trans7);
        
        String[] tran8 = {"q2", "q2"};
        ArrayList<String> trans8 = new ArrayList<>(Arrays.asList(tran8));
        map.put("q7", trans8);
        */
        String[] acctnce = {"d", "c", "e"};
        HashMap<String,ArrayList<String>> map = new HashMap<>();
        
        String[] tran = {"b", "c"};
        ArrayList<String> trans = new ArrayList<>(Arrays.asList(tran));
        map.put("a", trans);
        
        String[] tran2 = {"a", "d"};
        ArrayList<String> trans2 = new ArrayList<>(Arrays.asList(tran2));
        map.put("b", trans2);
        
        String[] tran3 = {"e", "f"};
        ArrayList<String> trans3 = new ArrayList<>(Arrays.asList(tran3));
        map.put("c", trans3);
        
        String[] tran4 = {"e", "f"};
        ArrayList<String> trans4 = new ArrayList<>(Arrays.asList(tran4));
        map.put("d", trans4);
        
        String[] tran5 = {"e", "f"};
        ArrayList<String> trans5 = new ArrayList<>(Arrays.asList(tran5));
        map.put("e", trans5);
        
        String[] tran6 = {"f", "f"};
        ArrayList<String> trans6 = new ArrayList<>(Arrays.asList(tran6));
        map.put("f", trans6);
        
        HashMap<String, ArrayList<String>> p1 = new HashMap<> ();
        
        ArrayList<String> acc_group = new ArrayList<> ();
        for (int i = 0; i < acctnce.length; i++){
            acc_group.add(acctnce[i]);
        }
        p1.put("0", acc_group);
        
        ArrayList<String> not_acc_group = new ArrayList<> ();
        final Enumeration<String> strEnum = Collections.enumeration(map.keySet());
        while(strEnum.hasMoreElements()) {
            String key = strEnum.nextElement();
            if(!acc_group.contains(key)){
                not_acc_group.add(key);
            }
        }
        
        p1.put("1", not_acc_group);
        
        System.out.println(p1.toString());
        
        ArrayList<HashMap> all_partitions = new ArrayList<> ();
        all_partitions.add(p1);
        boolean completed = false;
        int loops = 0;
        while(!completed){
        
        //for(int i = 0; i<10; i++){
            HashMap<String, ArrayList<String>> curr_par = new HashMap<> ();
            
            HashMap<String, ArrayList<String>> prev_par = all_partitions.get(all_partitions.size() - 1);
        
        final Enumeration<String> strEnum2 = Collections.enumeration(map.keySet());
        while(strEnum2.hasMoreElements()) {
            String key = strEnum2.nextElement();
            ArrayList<String> transitions = map.get(key);
            
            String tran0 = transitions.get(0);
            String tran1 = transitions.get(1);
            
            String idx1 = "-1";
            String idx2 = "-2";
            
            for (Map.Entry<String, ArrayList<String>> entry : prev_par.entrySet())
            {
                String gr_key = entry.getKey();
                ArrayList<String> value = entry.getValue();
                if(value.contains(tran0)){
                    //idx1 = group.indexOf(tran0);
                    idx1 = gr_key;
                }
                if(value.contains(tran1)){
                    //idx2 = group.indexOf(tran1);
                    idx2 = gr_key;
                }
            }
            
            String new_group_name = idx1 + "" + idx2;
            boolean group_exists = curr_par.containsKey(new_group_name);
            ArrayList<String> new_list;
            if(group_exists){
                new_list = curr_par.get(new_group_name);
            }
            else{
                new_list = new ArrayList<> ();
            }
            
            new_list.add(key);
            curr_par.put(new_group_name, new_list);
        }
        int nm = 0;
        HashMap<String, ArrayList<String>> new_curr_par = new HashMap<> ();
        for (Map.Entry<String, ArrayList<String>> entry : curr_par.entrySet())
        {
            new_curr_par.put(""+nm, entry.getValue());
            nm++;
        }
        
        curr_par = new_curr_par;
        
        System.out.println(curr_par.toString());
        
        if(all_partitions.contains(curr_par)){
            completed = true;
        }
        else{
            all_partitions.add(curr_par);
        }
        
        
        
        
        
        loops++;
        if(loops>10){
            break;
        }
        
        }
        System.out.println(all_partitions.toString());
        
        HashMap<String, ArrayList<String>> best_par = all_partitions.get(all_partitions.size() - 1);
        System.out.println("Final:");
        System.out.println(best_par.toString());
        
        String[] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        
        HashMap<String,ArrayList<String>> reduced_map = new HashMap<>();
        ArrayList<String> red_acctnce = new ArrayList<String>();
        for (Map.Entry<String, ArrayList<String>> entry : best_par.entrySet())
        {
            String letter = alfabeto[Integer.parseInt(entry.getKey())];
            String first_state = entry.getValue().get(0);
            ArrayList<String> red_states = new ArrayList<>();
            red_states.add("--");
            red_states.add("--");
            String zero_map = map.get(first_state).get(0);
            String one_map = map.get(first_state).get(1);
            for (Map.Entry<String, ArrayList<String>> sub_entry : best_par.entrySet())
            {
                ArrayList<String> all_maps = sub_entry.getValue();
                if(all_maps.contains(zero_map)){
                    red_states.set(0, alfabeto[Integer.parseInt(sub_entry.getKey())]);
                    
                }
                if(all_maps.contains(one_map)){
                    red_states.set(1, alfabeto[Integer.parseInt(sub_entry.getKey())]);
                }
            }
            
            reduced_map.put(letter, red_states);
        }
        
        System.out.println(reduced_map.toString());
    }
}
