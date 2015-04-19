package net.learn2develop.BasicViews1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class piefragment extends Fragment {
    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA};

    private static int[] VALUES = new int[] { 10, 11, 12};

    private static String[] NAME_LIST = new String[] { "Protein", "Carbs", "Fat"};

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private GraphicalView mChartView;

    private piechartinterface mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piefragment, container, false);
        return view;
    }
    public static final piefragment newInstance(int title, String message)
    {
        piefragment f = new piefragment();
        Bundle bdl = new Bundle(2);

        f.setArguments(bdl);
        return f;
    }
    public piefragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) getView().findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            layout.addView(mChartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        }
        else {
            mChartView.repaint();
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       try {
           mListener = (piechartinterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                   + " must implement OnFragmentInteractionListener");
        }
       VALUES = mListener.getNumbers();
        if(VALUES[0] + VALUES[1] + VALUES[2] == 0 ){
            //if all 0s I want the fragment to delete itself
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }
       // Toast.makeText(getActivity(),VALUES[0] + " " + VALUES[1] + " " + VALUES[2],Toast.LENGTH_LONG).show();
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setShowLegend(false);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        mRenderer.setStartAngle(90);
              for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        if (mChartView != null) {
            mChartView.repaint();
        }

    }


  public void refresh()
  {
      VALUES = mListener.getNumbers();
      mSeries.clear();
      for (int i = 0; i < VALUES.length; i++) {
          mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
          SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
          renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
          mRenderer.addSeriesRenderer(renderer);
      }

      if (mChartView != null) {
          mChartView.repaint();
      }
  }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface piechartinterface {
        // TODO: Update argument type and name
        public int[] getNumbers();
    }

}
