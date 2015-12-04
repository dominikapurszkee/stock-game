package pl.dominika.mapper;

import java.util.ArrayList;
import java.util.List;

import pl.dominika.entity.DataPriceEntity;
import pl.dominika.to.ShareTo;

public class Mapper {
	public static ShareTo convertFromEntity2To(DataPriceEntity dataPriceEntity) {
		if (dataPriceEntity != null) {
			return new ShareTo(dataPriceEntity.getCompany().getName(),
					dataPriceEntity.getPrice());
		}
		return null;
	}

	public static List<ShareTo> convertFromEntity2ToList(
			List<DataPriceEntity> dataPriceEntityList) {

		List<ShareTo> shareToList = new ArrayList<ShareTo>();
		if (dataPriceEntityList != null) {
			for (DataPriceEntity dataPriceEntity : dataPriceEntityList) {
				shareToList.add(convertFromEntity2To(dataPriceEntity));
			}
			return shareToList;
		}
		return null;
	}
}
