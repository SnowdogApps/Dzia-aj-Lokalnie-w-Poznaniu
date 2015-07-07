package pl.snowdog.dzialajlokalnie.fragment;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.IssuesAdapter;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 01.07.15.
 */

@EFragment(R.layout.fragment_list)
public class IssuesFragment extends ListFragment {

    IssuesAdapter adapter;

    @Override
    protected void afterView() {
        List<Issue> issues = new ArrayList<>();

        // TODO remove temp data
        issues.add(new Issue(0, 0, 0, "Łaszegerege", "Co mówi kabel do kabla?\n" +
                "- Bądźmy w kontakcie!", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 4, "1", "http://nie ma takiego lpiku.jpg", 0, 3, 34));
        issues.add(new Issue(0, 0, 0, "Zepsuta ławka", "Bardzo zepsuta", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 32, "2", "http://d.naszemiasto.pl/kadr/k/r/80/cb/51826a272c0d9_o,size,200x140,q,70,h,c775ac.jpg", 1, 3, 34));
        issues.add(new Issue(0, 0, 0, "Grill osiedlowy na ławce dla wszystkich pamperków", "Facet kupił fajny, duży telewizor, przyniósł do domu, żona patrzy, na pudle sporo jakichś znaczków informacyjnych. Zaciekawiona pyta:\n" +
                "- Kochanie, co oznacza ta szklanka na opakowaniu?\n" +
                "- To znaczy, że zakup trzeba opić.", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 178, "57", "http://i.wp.pl/a/f/jpeg/29034/thstck_grill_jedzenie_600.jpeg", 3, 3, 34));
        issues.add(new Issue(0, 0, 0, "NOINSOS V ( ENIEW BIW EOUFE OWE", "Co mówi kabel do kabla?\n" +
                "- Bądźmy w kontakcie!", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 4723, "0", "http://nie ma takiego lpiku.jpg", 4, 3, 34));
        issues.add(new Issue(0, 0, 0, "Zepsuta ławka", "Bardzo zepsuta", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 32, "2", "http://d.naszemiasto.pl/kadr/k/r/80/cb/51826a272c0d9_o,size,200x140,q,70,h,c775ac.jpg", 4, 3, 34));
        issues.add(new Issue(0, 0, 0, "Grill osiedlowy na ławce dla wszystkich pamperków", "Facet kupił fajny, duży telewizor, przyniósł do domu, żona patrzy, na pudle sporo jakichś znaczków informacyjnych. Zaciekawiona pyta:\n" +
                "- Kochanie, co oznacza ta szklanka na opakowaniu?\n" +
                "- To znaczy, że zakup trzeba opić.", 0, 12.13, 12.14, "Naramowicka 143", null, null, new Date(), 178, "57", "http://i.wp.pl/a/f/jpeg/29034/thstck_grill_jedzenie_600.jpeg", 5, 3, 34));

//        adapter = new IssuesAdapter(issues);
//        recyclerView.setAdapter(adapter);

        getIssues();
    }

    @Override
    protected void refreshItems() {
        getIssues();
    }

    @Override
    protected void issuesResult(List<Issue> issues) {
        adapter = new IssuesAdapter(issues);
        recyclerView.setAdapter(adapter);

        onItemsLoadComplete();
    }
}
