package com.example.registerzvent.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.registerzvent.R
import com.example.registerzvent.databinding.FragmentRegistryBinding
import com.example.registerzvent.models.Guest
import com.example.registerzvent.views.registryFragmentDirections

/**
 * Clase de el fragmento de registro
 */
class registryFragment : Fragment() {   
    /*Algo importante es que no deje datos quemados
     *Todos los calculos estan basados en el tamaño
     * de la lista de los invitados y en otras variables
     *
     * Se hizo asi para que en un futuro si se modifica
     * sea de una forma más facil
    */
    
    private var guest=
        Guest() //variable para databinding en el fragment
    private lateinit var binding:FragmentRegistryBinding

    //La lista que va a contener a todos los invitados exitentes, al menos en este caso
    private var guests_register: MutableList<Guest> = mutableListOf(
        Guest(
            name = "Andrea Vasquez",
            phone = "+502 8730 4739",
            email = "andyvas99@gmail.com"
        ),
        Guest(
            name = "Jorge Martínez",
            phone = "+502 9543 8563",
            email = "jorge_mart@gmail.com"
        ),
        Guest(
            name = "Javier Pérez",
            phone = "+502 6674 2320",
            email = "jav_perez01@gmail.com"
        ),
        Guest(
            name = "Rodígo Zamora",
            phone = "+502 4324 0385",
            email = "zamora_rodrigo@gmail.com"
        ),
        Guest(
            name = "Kevin Vielman",
            phone = "+502 9354 3210",
            email = "kev_vielman8968@gmail.com"
        ),
        Guest(
            name = "Nancy Alburez",
            phone = "+502 8463 1200",
            email = "nancy_al_85@gmail.com"
        ),
        Guest(
            name = "José Hernández",
            phone = "+502 5830 2599",
            email = "joseh15153@gmail.com"
        ),
        Guest(
            name = "Rodrigo Perez",
            phone = "+502 5533 8953",
            email = "rope085@gmail.com"
        ),
        Guest(
            name = "Alejandra Gutierrez",
            phone = "+502 2428 0000",
            email = "ale_gut328932@gmail.com"
        ),
        Guest(
            name = "Juan Orellana",
            phone = "+502 2424 1777",
            email = "juanor17783@gmail.com"
        )
    )

    //Contador de las posiciones
    private var contador =0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = DataBindingUtil.inflate<FragmentRegistryBinding>(inflater,
             R.layout.fragment_registry,container,false)

        //Se asocia el modelo descrito en esta clase, con el de la vista
        binding.guest = guest


        //Le asigno el primer valor de la lista para que al iniciar, ya este alli
        binding.guest=guests_register[contador]
        //fijo los datos
        binding.invalidateAll()

        //Dato inicial del action bar para saber como va la progrsion de los invitados
        (activity as AppCompatActivity).supportActionBar?.title = "Registrando" + " ("+(contador+1)+"/"+guests_register.size+")"

        //Es para que se pueda hacer botones en el action bar
        setHasOptionsMenu(true)
        return binding.root
    }








    /**
     * Esta funcion hace los calculos para mandar como
     * parametros al otro fragment, y al finalizarlos
     * inicia el nuevo fragment y manda los parametros
     */
    private fun moveToActivity(){

        //Se inicializa mensaje asi ya que sino va a tener una , en el inicio
        var mensaje = guests_register[0].name + ": " +guests_register[0].registered
        var posicion = 0 //posicion de la lista en la que esta posicionado
        var registrados = 0//cantidad de personas registradas existentes

        //Aqui hago el loop para que recorra toda la lista
        while (posicion<guests_register.size){
            mensaje +=", "+guests_register[posicion].name + ": " +guests_register[posicion].registered //Agrega a el string de resumen

            //Si esta registrado va a sumar 1 al contador de registrados
            if(guests_register[posicion].registered == "Si"){
                registrados++;
            }
            //se suma una posicion
            posicion++
        }
        mensaje += "." //Se agrega punto final al mensaje


        //llamado al otro fragment
        view?.findNavController()
            ?.navigate(
                registryFragmentDirections.actionRegistryFragmentToResultsFragment(
                    registrados,
                    guests_register.size,
                    mensaje
                )
            )
    }


    /**
     * Crea el registry menu, causando a[i que ya se puedan ver los botones
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.registry_menu, menu)

    }


    /**
     * Funciona como tipo click listener, solo que dependiendo del boton seleccionado
     * hace una accion distinta, la cual se decide con el when
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.guest_accepted -> {

                guests_register[contador].registered = "Si"
                contador++
                if(contador >= guests_register.size){
                    //Lanzo nuevo fragment
                    moveToActivity()
                }
                else{
                    //modifica el action bar con respecto a cuantos personas hayan pasado el registro

                    //Modifica el texto de la action bar para mostrar estado de invitados
                    (activity as AppCompatActivity).supportActionBar?.title = "Registrando" + " ("+(contador+1)+"/"+guests_register.size+")"
                    //cambia de guest para mostrarlo
                    binding.guest=guests_register[contador]
                    binding.invalidateAll()
                }

            }

            R.id.guest_denied -> {

                contador++
                //Aqui no coloque un set al No, ya que esta definido por defecto

                if(contador >= guests_register.size){
                    //Lanzo nuevo fragment
                    moveToActivity()
                }
                else{
                    //modifica el action bar con respecto a cuantas personas hayan pasado el registro
                    //Modifica el texto de la action bar para mostrar estado de invitados
                    (activity as AppCompatActivity).supportActionBar?.title = "Registrando" + " ("+(contador+1)+"/"+guests_register.size+")"
                    //cambia de guest para mostrarlo
                    binding.guest=guests_register[contador]
                    binding.invalidateAll()//hace el refresh para nuevos datos en el modelo Guest
                }


            }
        }
        return super.onOptionsItemSelected(item)
    }


}
