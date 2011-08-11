// ------------------------------------------------
// OPCoach Training Projects
// � OPCoach 2009 http://www.opcoach.com
// ------------------------------------------------

package com.opcoach.training.rental.ui.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;
import com.opcoach.training.rental.ui.RentalUIActivator;
import com.opcoach.training.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.ui.views.AgencyContentProvider.TNode;

/**
 * @author olivier
 */
public class AgencyLabelProvider extends LabelProvider implements IColorProvider, RentalUIConstants
{	
	/** The choosen palette among the addtional (may be null) */
	private IColorProvider currentPalette;


	public AgencyLabelProvider()
	{
		initPalette();
	}

	private SimpleDateFormat df = new SimpleDateFormat("dd/MM");

	@Override
	public String getText(Object element)
	{
		String result = null;
		boolean displayCount = RentalUIActivator.getDefault().getPreferenceStore().getBoolean(DISPLAY_COUNT_PREF);

		if (element instanceof RentalAgency)
		{
			result = ((RentalAgency) element).getName();
		} else if (element instanceof TNode)
		{
			TNode t = (TNode) element;
			if (CUSTOMERS_NODE == t.name)
			{
				result = CUSTOMERS_NODE + (displayCount ? "(" + t.agency.getCustomers().size() + ")" : "");
			} else if (RENTALS_NODE == t.name)
			{
				result = RENTALS_NODE + (displayCount ? "(" + t.agency.getRentals().size() + ")" : "");
			} else if (OBJECTS_NODE == t.name)
			{
				result = OBJECTS_NODE + (displayCount ? "(" + t.agency.getObjectsToRent().size() + ")" : "");
			}
		}

		else if (element instanceof Customer)
		{
			result = ((Customer) element).getDisplayName();
		} else if (element instanceof RentalObject)
		{
			result = ((RentalObject) element).getName();
		} else if (element instanceof Rental)
		{
			Rental r = (Rental) element;
			Date start = r.getStartDate();
			Date end = r.getEndDate();
			StringBuilder sb = new StringBuilder(r.getRentedObject().getName());
			sb.append(" [").append(df.format(start)).append("->").append(df.format(end)).append("]");
			result = sb.toString();
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object element)
	{
		return (currentPalette == null) ? null : currentPalette.getBackground(element);

	}

	@Override
	public Image getImage(Object element)
	{
		// TODO Auto-generated method stub
		Image result = null;
		ImageRegistry reg = RentalUIActivator.getDefault().getImageRegistry();

		if (element instanceof RentalAgency)
		{
			result = reg.get(AGENCY_KEY);
		} else if (element == RENTALS_NODE)
		{
			result = reg.get(RENTAL_KEY);
		} else if (element == CUSTOMERS_NODE)
		{
			result = reg.get(CUSTOMER_KEY);
		} else if (element == OBJECTS_NODE)
		{
			result = reg.get(RENTAL_OBJECT_KEY);
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(Object element)
	{
		if (currentPalette != null)
			return currentPalette.getForeground(element);
		else
		{
			if (element instanceof Customer)
				return getPrefColor(CUSTOMER_KEY);
			else if (element instanceof Rental)
				return getPrefColor(RENTAL_KEY);
			else if (element instanceof RentalObject)
				return getPrefColor(RENTAL_OBJECT_KEY);
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		}
	}

	/** A private methode to get a color in the preference store */
	private Color getPrefColor(String key)
	{
		IPreferenceStore pref = RentalUIActivator.getDefault().getPreferenceStore();
		String rgbKey = pref.getString(key);
		ColorRegistry reg = JFaceResources.getColorRegistry();
		Color result = reg.get(rgbKey);
		if (result == null)
		{
			// Get value in pref store
			reg.put(rgbKey, StringConverter.asRGB(rgbKey));
			result = reg.get(rgbKey);
		}
		return result;

	}
	
	public void initPalette()
	{
		// Recupere la palette selectionn�e dans les preferences
		// (appel� par le listener de preference store de l'agency view et par le constructeur du label provider)
		String val = RentalUIActivator.getDefault().getPreferenceStore().getString(COLOR_PROVIDER);
		currentPalette = (val == null) ? null : RentalUIActivator.getDefault().getPaletteManager().get(val);

	}

}
