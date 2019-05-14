package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.BusinessYear;
import com.ftn.model.WarehouseCard;
import com.ftn.model.WarehouseCardAnalytics;
import com.ftn.model.dto.*;
import com.ftn.repository.BusinessYearDao;
import com.ftn.repository.WarehouseCardDao;
import com.ftn.service.WarehouseCardService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 31.5.2017.
 */
@Service
public class WarehouseCardServiceImplementation implements WarehouseCardService {

    private final WarehouseCardDao warehouseCardDao;

    private final BusinessYearDao businessYearDao;

    @Autowired
    public WarehouseCardServiceImplementation(WarehouseCardDao warehouseCardDao, BusinessYearDao businessYearDao) {
        this.warehouseCardDao = warehouseCardDao;
        this.businessYearDao = businessYearDao;
    }

    @Override
    public List<WarehouseCardDTO> read() {
        return warehouseCardDao.findAll().stream().map(WarehouseCardDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<WarehouseCardDTO> read(Long warehouseID) {
        return warehouseCardDao.findByWarehouseId(warehouseID).stream().map(WarehouseCardDTO::new).collect(Collectors.toList());
    }

    @Override
    public WarehouseCardDTO read(WareDTO wareDTO, BusinessYear businessYear, WarehouseDTO warehouseDTO) {
        WarehouseCard warehouseCard = warehouseCardDao.findByWareIdAndBusinessYearIdAndWarehouseId(wareDTO.getId(), businessYear.getId(), warehouseDTO.getId());
        if(warehouseCard != null){
            return new WarehouseCardDTO(warehouseCard, true);
        }else {
            return null;
        }
    }

    @Override
    public WarehouseCard create(WarehouseCard warehouseCard) {
        if (warehouseCardDao.findById(warehouseCard.getId()).isPresent()) {
            throw new BadRequestException();
        }

        return warehouseCardDao.save(warehouseCard);
    }

    @Override
    public WarehouseCardDTO update(Long id, WarehouseCardDTO warehouseCardDTO) {
        final WarehouseCard warehouseCard = warehouseCardDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCard.merge(warehouseCardDTO);
        warehouseCardDao.save(warehouseCard);
        return new WarehouseCardDTO(warehouseCard);
    }

    @Override
    public void delete(Long id) {
        final WarehouseCard warehouseCard = warehouseCardDao.findById(id).orElseThrow(NotFoundException::new);
        warehouseCardDao.delete(warehouseCard);
    }

    @Override
    public String generateReport(ReportDataDTO reportDataDTO) {
        Optional<WarehouseCard> warehouseCard = warehouseCardDao.findById(reportDataDTO.getWarehouseCardDTO().getId());
        String jasperFilePath = "src/main/resources/jasper/MagacinskaKartica.jasper";

        ArrayList<WarehouseCardReportDTO> tableItems = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();

        for(WarehouseCardAnalytics warehouseCardAnalytics : warehouseCard.get().getWarehouseCardAnalytics()) {
            Date createdDate = formatDate(warehouseCardAnalytics.getCreated());
            if(createdDate.compareTo(reportDataDTO.getStartDate()) >= 0 && createdDate.compareTo(reportDataDTO.getEndDate()) <= 0) {
                WarehouseCardReportDTO warehouseCardReportDTO = new WarehouseCardReportDTO(warehouseCardAnalytics);
                tableItems.add(warehouseCardReportDTO);
            }
        }
        data.put("CompanyName", warehouseCard.get().getWarehouse().getCompany().getName());
        data.put("WarehouseName", warehouseCard.get().getWarehouse().getName());
        data.put("StartDate", reportDataDTO.getStartDate());
        data.put("EndDate", reportDataDTO.getEndDate());
        data.put("WareId", warehouseCard.get().getWare().getId());
        data.put("WareName", warehouseCard.get().getWare().getName());
        data.put("Packing", warehouseCard.get().getWare().getPacking());
        data.put("MeasurementUnit", warehouseCard.get().getWare().getMeasurementUnit().getLabel());
        data.put("DataSource", new JRBeanCollectionDataSource(tableItems));
        try{
            String filePath = "src/main/resources/warehouseCardReports/magacinskaKartica" + getNextFileCounter() +  ".pdf";
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, data, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

            File file = new File("C:\\Users\\Jasmina\\magacinskaKartica.pdf");
            OutputStream outputStream = new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            Path path = Paths.get("C:\\Users\\Jasmina\\magacinskaKartica.pdf");
            byte[] dataPDF = Files.readAllBytes(path);
            byte[] encodedBytes = Base64.getEncoder().encode(dataPDF);
            return new String(encodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Date formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    @Override
    public WarehouseCardDTO getWarehouseCardForWare(WareDTO wareDTO) {
        List<BusinessYear> businessYearList = businessYearDao.findByClosed(false);
        BusinessYear businessYear = businessYearList.get(0);
        WarehouseCard card = warehouseCardDao.findByWareIdAndBusinessYearId(wareDTO.getId(), businessYear.getId());
        if (card == null) {
            return null;
        }
        return new WarehouseCardDTO(card, true);
    }


    private static int getNextFileCounter() {
        int counter = 0;
        File folder = new File("src/main/resources/warehouseCardReports");
        for (File f : folder.listFiles()){
            if (f.isFile()){
                String name = f.getName();
                try{
                    counter = Integer.parseInt(name.substring(name.lastIndexOf('a') + 1, name.indexOf('.')));
                    counter++;
                }catch(Exception e){
                    continue;}
            }
        }
        return counter;
    }
}
