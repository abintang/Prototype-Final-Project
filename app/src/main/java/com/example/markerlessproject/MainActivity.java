package com.example.markerlessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

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

    FloatingActionButton deleteButton;

    ImageView closebutton;
    ExtendedFloatingActionButton gantiModel;

    private AnchorNode anchorNode;
    private TransformableNode model;

    BottomSheetBehavior sheetBehavior;
    ConstraintLayout bottomSheet;

    private MaterialCardView cardMerkurius, cardVenus, cardBumi, cardMars, cardJupiter, cardSaturnus, cardUranus, cardNeptunus;

    public String value;
    TextView namaPlanet;

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
            loadModels();
        }

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
        } else if (mars) {
            textView.setText("Mars");
        } else if (earth) {
            textView.setText("Bumi");
            buttonDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MenuMateriActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        } else if (jupiter) {
            textView.setText("Jupiter");
        } else if (saturn) {
            textView.setText("Saturnus");
        } else if (uranus) {
            textView.setText("Uranus");
        } else if (neptunus) {
            textView.setText("Neptunus");
        } else if (merkurius) {
            textView.setText("Merkurius");
        }


    }

}