package com.leelah.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.leelah.android.R;
import com.leelah.android.bo.Product;
import com.smartnsoft.droid4me.framework.SmartAdapters.BusinessViewHolder;
import com.smartnsoft.droid4me.framework.SmartAdapters.SimpleBusinessViewWrapper;

public class ProductDetailsDialogFragment
    extends DialogFragment
{

  private static final class ProductDetailsAttributes
  {

    private final TextView productName;

    public ProductDetailsAttributes(View view)
    {
      productName = (TextView) view.findViewById(R.id.productName);
    }

    public void update(Product businessObject)
    {
      productName.setText("LOOOOOOL");
    }
  }

  private final class ProductDetailsWrapper
      extends SimpleBusinessViewWrapper<Product>
  {

    public ProductDetailsWrapper(Product businessObject)
    {
      super(businessObject, 0, R.layout.product_details);
    }

    @Override
    protected Object extractNewViewAttributes(Activity activity, View view, Product businessObject)
    {
      return new ProductDetailsAttributes(view);
    }

    @Override
    protected void updateView(Activity activity, Object viewAttributes, View view, Product businessObject, int position)
    {
      ((ProductDetailsAttributes) viewAttributes).update(businessObject);
    }

  }

  enum ActionType
  {
    ViewProduct, ModifyProductCart
  }

  private static final String PRODUCT = "product";

  private static final String ACTION = "action";

  public static ProductDetailsDialogFragment newInstance(ActionType action, Product product)
  {
    final ProductDetailsDialogFragment fragment = new ProductDetailsDialogFragment();
    final Bundle bundle = new Bundle();
    bundle.putSerializable(PRODUCT, product);
    bundle.putSerializable(ACTION, action);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState)
  {
    final Product product = (Product) getArguments().getSerializable(PRODUCT);
    final ActionType action = (ActionType) getArguments().getSerializable(ACTION);

    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final BusinessViewHolder<Product> viewHolder = new BusinessViewHolder<Product>(new ProductDetailsWrapper(product));
    builder.setView(viewHolder.getView(getActivity()));
    viewHolder.updateView(getActivity());
    return builder.create();
  }
}