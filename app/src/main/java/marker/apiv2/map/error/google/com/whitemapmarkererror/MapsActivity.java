package marker.apiv2.map.error.google.com.whitemapmarkererror;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker[] markers = new Marker[50];
    final LatLng start = new LatLng(59.321689, 18.0842141);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(59.32619371306108, 18.090061135590076), 17f)));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                System.out.println(cameraPosition);
            }
        });

        for(int i = 0; i < markers.length; i++) {
            markers[i] = mMap.addMarker(new MarkerOptions().position(start).icon(BitmapDescriptorFactory.fromResource(R.drawable.enemynew)));
        }

        new Thread(moveAroundAndChangeIconsOnMarkersList).start();
    }

    private Runnable moveAroundAndChangeIconsOnMarkersList = new Runnable() {
        @Override
        public void run() {

            while (true) {

                final Random rand = new Random();

                final double posLo = start.longitude + rand.nextDouble() * 0.01;
                final double posLa = start.latitude + rand.nextDouble() * 0.01;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        markers[rand.nextInt(markers.length - 1)].setPosition(new LatLng(posLa, posLo));
                        markers[rand.nextInt(markers.length - 1)].setIcon(BitmapDescriptorFactory.fromResource(R.drawable.enemynew));
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    };
}
