/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funmatnb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zebas
 */
public class Reduce {
    // OJO que esto asume que el archivo está en el directorio 'funmatnb'
    private static final String test_file = "test.txt";
    private static final String out_file = "out_"+ test_file;

    private static final String[] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private static HashMap<String, ArrayList<String>> read_desc(String file_loc){
        HashSet acceptance_states = new HashSet<>();
        HashMap<String, ArrayList<String>> read_map = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(test_file))) {
            int line_num = 0;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
                String[] columnDetail = line.split("\t", -1);
                System.out.println(Arrays.toString(columnDetail));

                String[] tran = {columnDetail[0], columnDetail[2]};
                ArrayList<String> trans = new ArrayList<>(Arrays.asList(tran));
                read_map.put(alfabeto[line_num], trans);

                if(columnDetail[1].equals("1")){
                    acceptance_states.add(columnDetail[0]);
                }

                if(columnDetail[3].equals("1")){
                    acceptance_states.add(columnDetail[2]);
                }

                line_num++;
             }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reduce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reduce.class.getName()).log(Level.SEVERE, null, ex);
        }

        read_map.put("____acc", new ArrayList<String>(acceptance_states));

        System.out.println(read_map.toString());
        System.out.println(acceptance_states.toString());
        return read_map;
    }

    private static void write_desc(HashMap<String, ArrayList<String>> autom){
        ArrayList<String> acceptance_states = autom.remove("____acc");
        HashMap<String, ArrayList<String>> read_map = new HashMap<>();
        System.out.println(System.getProperty("user.dir"));
        try{
            PrintWriter writer = new PrintWriter(out_file, "UTF-8");
            // Make sure we visit all keys in order
            ArrayList<String> keys = new ArrayList<>(autom.keySet());
            Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
            for (String key : keys){
                ArrayList<String> valu = autom.get(key);
                String trans0 = valu.get(0);
                String trans1 = valu.get(1);

                String acc0 = acceptance_states.contains(trans0) ? "1" : "0";
                String acc1 = acceptance_states.contains(trans1) ? "1" : "0";

                writer.println(trans0 + "\t" + acc0 + "\t" + trans1 + "\t" + acc1 + "\t");
            }

            writer.close();
        } catch (IOException e) {
           // do something
        }
    }

    public static void main(String[] args){

        boolean leave_acc_alone = true;
        boolean debug_verbose = false;

        HashMap<String,ArrayList<String>> map = read_desc("file");
        ArrayList<String> acctnce = map.remove("____acc");

        HashMap<String, ArrayList<String>> p1 = new HashMap<> ();

        ArrayList<String> acc_group = new ArrayList<> ();
        for (int i = 0; i < acctnce.size(); i++){
            acc_group.add(acctnce.get(i));
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

                if(debug_verbose) System.out.println("Checking: " + key);

                ArrayList<String> transitions = map.get(key);

                String tran0 = transitions.get(0);
                String tran1 = transitions.get(1);

                if(debug_verbose) System.out.println("Tran: " + tran0 + "," +tran1);

                String idx1 = "-1";
                String idx2 = "-2";

                for (Map.Entry<String, ArrayList<String>> entry : prev_par.entrySet())
                {
                    String gr_key = entry.getKey();
                    ArrayList<String> value = entry.getValue();
                    if(value.contains(tran0)){
                        if(debug_verbose) System.out.println("Prev G contained: " + tran0 + "_" + value + "," + gr_key);
                        //idx1 = group.indexOf(tran0);
                        idx1 = gr_key;
                    }
                    if(value.contains(tran1)){
                        if(debug_verbose) System.out.println("Prev G contained: " + tran1 + "_" + value + "," + gr_key);
                        //idx2 = group.indexOf(tran1);
                        idx2 = gr_key;
                    }
                }

                String new_group_name = idx1 + "" + idx2;

                if(acc_group.contains(key) && leave_acc_alone){
                    new_group_name = "_a";
                }

                if(debug_verbose) System.out.println("New G: " +new_group_name);
                if(debug_verbose) System.out.println("Curr Map: " +curr_par.toString());

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

            if(debug_verbose) System.out.println(curr_par.toString());

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
        if(debug_verbose) System.out.println(all_partitions.toString());

        HashMap<String, ArrayList<String>> best_par = all_partitions.get(all_partitions.size() - 1);
        System.out.println("Final:");
        System.out.println(best_par.toString());
        System.out.println(best_par.values().toString());

        // Find the new state with A and make it the first sate
        String key_with_A = best_par.keySet().iterator().next();
        for (Map.Entry<String, ArrayList<String>> entry : best_par.entrySet())
        {
            if(entry.getValue().contains("A")){
                key_with_A = entry.getKey();
            }
        }

        if(!key_with_A.equals("0")){
            ArrayList <String> a_group = best_par.remove(key_with_A);
            ArrayList <String> Ogrup = best_par.remove("0");

            best_par.put("0", a_group);
            best_par.put(key_with_A, Ogrup);
        }

        // Make sure we visit all keys in order
        ArrayList<String> keys = new ArrayList<>(best_par.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);

        //ArrayList<String> all_keys = new ArrayList<String>(best_par.keySet());
        //System.out.println(all_keys.toString());

        HashMap<String,ArrayList<String>> reduced_map = new HashMap<>();
        HashSet<String> reduced_acceptance_states = new HashSet<>();
        ArrayList<String> red_acctnce = new ArrayList<String>();
        System.out.println(best_par);
        for (String key : keys)
        {
            ArrayList<String> grped_states = best_par.get(key);
            String first_state = grped_states.get(0);
            String letter = "__";
            letter = alfabeto[Integer.parseInt(key)];

            for(String st : best_par.get(key)){
                    if(acctnce.contains(st)){
                        //System.out.println("Acct contains: " + st);
                        //System.out.println("Adding: " + letter);
                        reduced_acceptance_states.add(letter);
                    }
             }

            ArrayList<String> red_states = new ArrayList<>();
            red_states.add("--");
            red_states.add("--");
            String zero_map = map.get(first_state).get(0);
            String one_map = map.get(first_state).get(1);
            for (Map.Entry<String, ArrayList<String>> sub_entry : best_par.entrySet())
            {
                ArrayList<String> all_maps = sub_entry.getValue();
                //System.out.println(all_maps.toString());
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
    System.out.println(reduced_acceptance_states.toString());
    reduced_map.put("____acc", new ArrayList<String>(reduced_acceptance_states));
    write_desc(reduced_map);
    }
}
