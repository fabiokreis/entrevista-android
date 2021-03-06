package br.com.starwarswiki.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.anvil.FrameLayoutComponent
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

abstract class ReactiveActivity: AppCompatActivity(), StateListener<AppState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialState()
        setContentView(object: FrameLayoutComponent(this ) {
            override fun view() {
                content()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        StarWarsApplication.redukt.listeners.add(this)
    }

    override fun onStop() {
        StarWarsApplication.redukt.listeners.remove(this)
        super.onStop()
    }

    abstract fun initialState()

    abstract fun content()
}