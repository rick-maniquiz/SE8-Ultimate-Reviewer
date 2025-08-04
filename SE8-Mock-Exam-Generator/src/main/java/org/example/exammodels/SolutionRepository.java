package org.example.exammodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.topicmodels.Topic;
import org.example.topicmodels.TopicRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SolutionRepository {

    private static SolutionRepository instance = new SolutionRepository();
    private static List<SolutionEntry> solutions;

    public SolutionRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("solutions/solution-repository.json");
        try {
            solutions = mapper.readValue(jsonFile, new TypeReference<List<SolutionEntry>>() {});

        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static SolutionRepository getInstance() {
        return instance;
    }

    public static List<SolutionEntry> getSolutions() {
        return solutions;
    }
}
