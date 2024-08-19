package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;

public class SimpleGoalRepositoryTest {

    @Test
    public void testFind() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        assertEquals(repo.find(0).getValue(), goals.get(0));
    }

    @Test
    public void testFindAll() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        assertEquals(repo.findAll().getValue(), goals);
    }

    @Test
    public void testFindAllTmr(){
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(new Goal("5", 5, true, 5,"Tomorrow", -1, 1),
                new Goal("6", 6, true, 6,"Tomorrow", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        assertEquals(repo.findAllTmr().getValue(), goals);
    }

    @Test
    public void testFindAllPending(){
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(new Goal("7", 7, true, 7,"Pending", -1, 1),
                new Goal("8", 8, true, 8,"Pending", -1, 1),
                new Goal("9", 9, true, 9,"Pending", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        assertEquals(repo.findAllPending().getValue(), goals);
    }

    @Test
    public void testSave() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);

        Goal expected = new Goal("Goal5", 4, false, 1,"Today", -1, 1);
        repo.save(expected);
        assertEquals(repo.find(4).getValue(), expected);
    }

    @Test
    public void testSaveAndAppend() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, true, 3,"Today", -1, 1),
                new Goal("Goal4", 3, true, 4,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);

        repo.saveAndAppend(new Goal("Goal5", null, false, -1,"Today", -1, 1));
        Goal expected = new Goal("Goal5", 4, false, 3,"Today", -1, 1);
        assertEquals(repo.find(4).getValue(), expected);
    }

    @Test
    public void testAppendCompleteGoal() {
        var dataSource = new InMemoryDataSource();
        List<Goal> goals = List.of(
                new Goal("Goal1", 0, false, 1,"Today", -1, 1),
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1)
        );
        dataSource.putGoals(goals);
        var repo = new SimpleGoalRepository(dataSource);
        repo.appendCompleteGoal(goals.get(0));

        List<Goal> expected = List.of(
                new Goal("Goal2", 1, false, 2,"Today", -1, 1),
                new Goal("Goal3", 2, false, 3,"Today", -1, 1),
                new Goal("Goal4", 3, false, 4,"Today", -1, 1),
                new Goal("Goal1", 0, false, 5,"Today", -1, 1)
        );
        assertEquals(repo.find(0).getValue().getId(), expected.get(3).getId());
        assertEquals(repo.find(0).getValue().getTitle(), expected.get(3).getTitle());
        //assertEquals(repo.find(0).getValue().isComplete(), expected.get(3).isComplete());
        //assertEquals(repo.find(0).getValue().getSortOrder(), expected.get(3).getSortOrder());
    }

}
