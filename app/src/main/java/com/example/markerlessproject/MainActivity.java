package com.example.markerlessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.RenderableInstance;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.InstructionsController;
import com.google.ar.sceneform.ux.TransformableNode;
import com.gorisse.thomas.sceneform.light.LightEstimationConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements FragmentOnAttachListener, BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener, ArFragment.OnViewCreatedListener {

    private ArFragment arFragment;
    private Renderable renderable;

    private ViewRenderable viewRenderable;

    private Boolean merkurius = false;
    private Boolean venus = false;
    private Boolean mars = false;
    private Boolean earth = false;
    private Boolean jupiter = false;
    private Boolean saturn = false;
    private Boolean uranus = false;
    private Boolean neptunus = false;

    private Boolean bulan = false;

    private Boolean satelit = false;

    private Boolean matahari = false;

    FloatingActionButton deleteButton;

    ImageView closebutton;
    ExtendedFloatingActionButton gantiModel;

    private AnchorNode anchorNode;
    private TransformableNode model;

    BottomSheetBehavior sheetBehavior;
    ConstraintLayout bottomSheet;

    MaterialCardView cardTutorial;
    ImageView iconTutorial;
    TextView textTutorial, skipTutorial;
    MaterialButton btnSelanjutnya, btnSelesai;


    private MaterialCardView cardMerkurius, cardVenus, cardBumi, cardMars, cardJupiter
            , cardSaturnus, cardUranus, cardNeptunus, cardBulan, cardSatelit, kategoriPlanet, kateriBintang, kategoriSatelit, cardMatahari;
    @SuppressLint("MissingInflatedId")
    public String value;
    TextView namaPlanet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        value = getIntent().getExtras().getString("id");

        View inflatedView = getLayoutInflater().inflate(R.layout.model, null);
        namaPlanet = (TextView) inflatedView.findViewById(R.id.tv_nama_card);

        bottomSheet = findViewById(R.id.bottomSheet);
        sheetBehavior =  BottomSheetBehavior.from(bottomSheet);

        cardTutorial = findViewById(R.id.cardTutorial);
        textTutorial = findViewById(R.id.textTutorial);
        iconTutorial = findViewById(R.id.iconTutorial);
        skipTutorial = findViewById(R.id.textLewatiTutorial);
        btnSelanjutnya = findViewById(R.id.btn_selanjutnya);
        btnSelesai = findViewById(R.id.btn_selesai);

        btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelanjutnya.setVisibility(View.INVISIBLE);
                btnSelesai.setVisibility(View.VISIBLE);
                iconTutorial.setImageResource(R.drawable.moving);
                textTutorial.setText("Arahkan kamera handphone ke objek datar seperti lantai dan gerakan handphone ke kiri dan kanan sampai titik-titik putih muncul dilayar !");
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTutorial.setVisibility(View.GONE);
            }
        });

        skipTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardTutorial.setVisibility(View.GONE);
            }
        });

        FloatingActionButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if(savedInstanceState == null) {
            if(Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        gantiModel = findViewById(R.id.gantiModel);
        gantiModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                gantiModel.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
            }
        });



        // button delete all 3d model from scene
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scene originalScene = arFragment.getArSceneView().getScene();
                removeAllModels(originalScene);

            }
        });

        closebutton = findViewById(R.id.closeIv);

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        cardMerkurius = findViewById(R.id.cardMerkurius);
        cardVenus = findViewById(R.id.cardVenus);
        cardBumi = findViewById(R.id.cardBumi);
        cardMars = findViewById(R.id.cardMars);
        cardJupiter = findViewById(R.id.cardJupiter);
        cardUranus = findViewById(R.id.cardUranus);
        cardSaturnus = findViewById(R.id.cardSaturn);
        cardNeptunus = findViewById(R.id.cardNeptunus);

        cardBulan = findViewById(R.id.cardMoon);
        cardSatelit = findViewById(R.id.cardSatelit);

        cardMatahari = findViewById(R.id.cardSun);

        kategoriPlanet = findViewById(R.id.kategoriPlanet);
        kategoriSatelit = findViewById(R.id.kategoriSatelit);
        kateriBintang = findViewById(R.id.kategoriBintang);

        kategoriPlanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(cardMerkurius);
                setVisible(cardVenus);
                setVisible(cardBumi);
                setVisible(cardMars);
                setVisible(cardJupiter);
                setVisible(cardSaturnus);
                setVisible(cardUranus);
                setVisible(cardNeptunus);

                setGone(cardMatahari);

                setGone(cardBulan);
                setGone(cardSatelit);

                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        kategoriSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGone(cardMerkurius);
                setGone(cardVenus);
                setGone(cardBumi);
                setGone(cardMars);
                setGone(cardJupiter);
                setGone(cardSaturnus);
                setGone(cardUranus);
                setGone(cardNeptunus);

                setGone(cardMatahari);

                setVisible(cardBulan);
                setVisible(cardSatelit);

                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        kateriBintang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGone(cardMerkurius);
                setGone(cardVenus);
                setGone(cardBumi);
                setGone(cardMars);
                setGone(cardJupiter);
                setGone(cardSaturnus);
                setGone(cardUranus);
                setGone(cardNeptunus);

                setVisible(cardMatahari);

                setGone(cardBulan);
                setGone(cardSatelit);

                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        cardMerkurius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                merkurius = true;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardVenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = true;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardBumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = true;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardMars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = true;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardJupiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = true;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardSaturnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = true;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardUranus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = true;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        cardNeptunus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = true;
                bulan = false;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        cardBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = true;
                satelit = false;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        cardSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = true;
                matahari = false;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        cardMatahari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merkurius = false;
                venus = false;
                earth = false;
                mars = false;
                jupiter = false;
                saturn = false;
                uranus = false;
                neptunus = false;
                bulan = false;
                satelit = false;
                matahari = true;
                loadModels();
                gantiModel.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        if(value.equalsIgnoreCase("Merkurius")) {
            merkurius = true;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Venus")) {
            merkurius = false;
            venus = true;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Bumi")) {
            merkurius = false;
            venus = false;
            earth = true;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Jupiter")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = true;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Saturnus")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = true;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Uranus")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = true;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Neptunus")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = true;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Mars")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = true;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Bulan")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = true;
            satelit = false;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Satelit")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = true;
            matahari = false;

            loadModels();
        } else if (value.equalsIgnoreCase("Matahari")) {
            merkurius = false;
            venus = false;
            earth = false;
            mars = false;
            jupiter = false;
            saturn = false;
            uranus = false;
            neptunus = false;

            bulan = false;
            satelit = false;
            matahari = true;

            loadModels();
        }

    }

    private void setVisible(MaterialCardView cardview) {
        cardview.setVisibility(View.VISIBLE);
    }

    private void setGone(MaterialCardView cardView) {
        cardView.setVisibility(View.INVISIBLE);
    }


    private void removeAllModels(Scene scene) {
        List<Node> nodes = new ArrayList<>(scene.getChildren());
        for (Node node : nodes) {
            if (node instanceof Camera) {
                removeAllChildren(node);
            } else {
                scene.removeChild(node);
            }
        }
    }

    private void removeAllChildren(Node node) {
        List<Node> children = new ArrayList<>(node.getChildren());
        for (Node child : children) {
            if (child instanceof Camera) {
                continue;
            }
            removeAllChildren(child);
            child.setParent(null);
        }
    }
    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }

        // Mengatur Autofocus kamera
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);
        try {
            session.resume();
        } catch (CameraNotAvailableException e) {
            throw new RuntimeException(e);
        }

        arFragment.getArSceneView()._lightEstimationConfig = LightEstimationConfig.DISABLED;
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener(null);
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);

        InstructionsController instructionsController = arFragment.getInstructionsController();
        if (instructionsController != null) {
            instructionsController.setEnabled(false);
        }
    }

    private void loadModels() {
        WeakReference<MainActivity> weakReference = new WeakReference<>(this);

        if (merkurius) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/mercury.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });

            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });

        } else if (venus) {
            this.namaPlanet.setText("Bumi");
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/venus.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });


        } else if (earth) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/earth.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });

            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (mars) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/mars.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });

            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });

        } else if (jupiter) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/jupiter.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });

            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (saturn) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/saturn.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (uranus) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/uranus.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (neptunus) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/neptune.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (bulan) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/moon.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (satelit) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/satellite.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        } else if (matahari) {
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("planets/sun.glb"))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(model -> {
                        MainActivity activity = weakReference.get();
                        if (activity != null) {
                            activity.renderable = model;
                        }
                    });
            ViewRenderable.builder()
                    .setView(this, R.layout.model)
                    .build()
                    .thenAccept(viewRenderable -> {
                        MainActivity activity = weakReference.get();

                        if (activity != null) {
                            activity.viewRenderable = viewRenderable;
                        }
                    });
        }
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (renderable == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        arFragment.getArSceneView().getPlaneRenderer().setShadowReceiver(false);
        Anchor anchor = hitResult.createAnchor();
        this.anchorNode = new AnchorNode(anchor);
        this.anchorNode.setParent(arFragment.getArSceneView().getScene());

        this.model = new TransformableNode(arFragment.getTransformationSystem());
        this.model.setParent(this.anchorNode);
        this.model.setRenderable(this.renderable)
                .animate(true).start();
        model.select();

        Node titleNode = new Node();
        titleNode.setParent(model);
        titleNode.setEnabled(false);
        if (jupiter || uranus || neptunus) {
            titleNode.setLocalPosition(new Vector3(0.0f, 0.55f, 0.0f));
        } else if (matahari) {
            titleNode.setLocalPosition(new Vector3(0.0f, 0.75f, 0.0f));
        } else {
            titleNode.setLocalPosition(new Vector3(0.0f, 0.35f, 0.0f));
        }
        titleNode.setRenderable(viewRenderable);
        titleNode.setEnabled(true);

        RenderableInstance renderableInstance = titleNode.getRenderableInstance();
        ViewRenderable viewRenderable1 = (ViewRenderable) renderableInstance.getRenderable();

        View view = viewRenderable1.getView();
        TextView textView = view.findViewById(R.id.tv_nama_card);
        Button buttonDetail = view.findViewById(R.id.buttonDetail);
        if (venus) {
            textView.setText("Venus");
            goToDetail(2, buttonDetail);
        } else if (mars) {
            textView.setText("Mars");
            goToDetail(4, buttonDetail);
        } else if (earth) {
            textView.setText("Bumi");
            goToDetail(3, buttonDetail);
        } else if (jupiter) {
            textView.setText("Jupiter");
            goToDetail(5, buttonDetail);
        } else if (saturn) {
            textView.setText("Saturnus");
            goToDetail(6, buttonDetail);
        } else if (uranus) {
            textView.setText("Uranus");
            goToDetail(7, buttonDetail);
        } else if (neptunus) {
            textView.setText("Neptunus");
            goToDetail(8, buttonDetail);
        } else if (merkurius) {
            textView.setText("Merkurius");
            goToDetail(1, buttonDetail);
        } else if (bulan) {
            textView.setText("Bulan");
            goToDetail(10, buttonDetail);
        } else if (satelit) {
            textView.setText("Satelit Buatan");
            goToDetail(11, buttonDetail);
        } else if (matahari) {
            textView.setText("Matahari");
            goToDetail(9, buttonDetail);
        }

    }

    private void goToDetail(int value , Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailMateri.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", value);
                startActivity(intent);
            }
        });

    }
}
