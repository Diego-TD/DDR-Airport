package com.ddr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.ddr.R;
import com.ddr.databinding.FragmentFlightsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FlightsFragment extends Fragment {

    private FragmentFlightsBinding binding;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private TextView oneWayButton;
    private TextView roundTripButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FlightsViewModel flightsViewModel =
                new ViewModelProvider(this).get(FlightsViewModel.class);

        binding = FragmentFlightsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);
        oneWayButton = root.findViewById(R.id.oneWayButton);
        roundTripButton = root.findViewById(R.id.roundTripButton);

        // Create an adapter for ViewPager2
        VpAdapter vap1Adapter = new VpAdapter(this);
        vap1Adapter.addFragment(new oneWay(), "OneWay");
        vap1Adapter.addFragment(new roundTrip(), "Round Trip");

        // Set the adapter to the ViewPager2
        viewPager.setAdapter(vap1Adapter);
        // Agregar el listener para manejar la selección de pestañas
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Cambiar la página del ViewPager2 según la pestaña seleccionada
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No es necesario implementar nada aquí
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No es necesario implementar nada aquí
            }
        });

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(vap1Adapter.getPageTitle(position).toString())
        ).attach();

        // Configurar OnClickListener para los botones invisibles del TabLayout
        oneWayButton.setOnClickListener(view -> viewPager.setCurrentItem(0));
        roundTripButton.setOnClickListener(view -> viewPager.setCurrentItem(1));

        // Configurar listener para el ViewPager2 para sincronizar la posición del TabLayout
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.setScrollPosition(position, 0f, true);
            }
        });

        final TextView textView = binding.bookYourFlightText;
        flightsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
