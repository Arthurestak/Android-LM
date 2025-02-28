package com.example.projetolm.ui.assinatura;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetolm.databinding.FragmentAssinaturaBinding;
import com.example.projetolm.databinding.FragmentAssinaturaBinding;

public class AssinaturaFragment extends Fragment {

    private FragmentAssinaturaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AssinaturaViewModel assinaturaViewModel =
                new ViewModelProvider(this).get(AssinaturaViewModel.class);

        binding = FragmentAssinaturaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        assinaturaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}