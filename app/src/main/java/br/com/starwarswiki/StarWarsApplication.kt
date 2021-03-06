package br.com.starwarswiki

import android.app.Application
import android.content.Context
import br.com.starwarswiki.database.ObjectBox
import br.com.starwarswiki.middlewares.DatabaseMiddleware
import br.com.starwarswiki.middlewares.PeopleMiddleware
import br.com.starwarswiki.middlewares.PlanetsMiddleware
import br.com.starwarswiki.middlewares.SpeciesMiddleware
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.reducers.*
import com.github.raulccabreu.redukt.Redukt

class StarWarsApplication : Application() {

    companion object {
        lateinit var redukt: Redukt<AppState>
    }

    private fun initRedukt(context: Context, appState: AppState): Redukt<AppState> {
        val redukt = Redukt(appState, true)

        addReducers(redukt)
        addMiddlewares(context, redukt)

        return redukt
    }

    private fun addReducers(redukt: Redukt<AppState>) {
        redukt.reducers["people_reducer"] = PeopleReducer()
        redukt.reducers["planets_reducer"] = PlanetsReducer()
        redukt.reducers["species_reducer"] = SpeciesReducer()
        redukt.reducers["database_reducer"] = DatabaseReducer()
        redukt.reducers["sync_reducer"] = SyncReducer()
    }

    private fun addMiddlewares(context: Context, redukt: Redukt<AppState>) {
        redukt.middlewares["people_middleware"] = PeopleMiddleware(context)
        redukt.middlewares["planets_middleware"] = PlanetsMiddleware(context)
        redukt.middlewares["species_middleware"] = SpeciesMiddleware(context)
        redukt.middlewares["database_middleware"] = DatabaseMiddleware()
    }

    override fun onCreate() {
        super.onCreate()

        ObjectBox.build(this)
        initRedukt(this, AppState(syncRunning = false)).let {
            redukt = it
        }
    }
}