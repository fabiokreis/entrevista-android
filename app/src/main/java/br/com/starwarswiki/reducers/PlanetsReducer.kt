package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Planet
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class PlanetsReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SAVE_PLANETS)
    fun savePlanets(state: AppState, payload: Map<String, Planet>): AppState {
        return state.copy(planets = payload)
    }
}