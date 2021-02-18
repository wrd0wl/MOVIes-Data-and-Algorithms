package src.implementation;

import src.commons.*;

/**
 * 
 * Interface that is used to observe the collaborations between actors, intended as participation in the same films.
 * 
 */

public interface ICollaborations {

    /**
     * 
     * Identify the direct collaborators of an actor.
     * 
     * @param actor get direct collaborators of <code> actor </code>.
     * @return array of actors.
     */
    public Person[] getDirectCollaboratorsOf(Person actor);

    /**
     * 
     * Get an actor's indirect collaborators.
     * 
     * @param actor get the team of <code> actor </code>.
     * @return array of actors.
     */
    public Person[] getTeamOf(Person actor);

    /**
     * 
     * Identify the set of characteristic collaborators of an actor's team, which maximizes the overall score.
     * 
     * @param actor maximize collaborations in the team of <code> actor </code>.
     * @return array of collaborators.
     */
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor);
    
}
