package com.ftn.service.implementation;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.User;
import com.ftn.model.Ware;
import com.ftn.model.Warehouse;
import com.ftn.model.WarehouseCard;
import com.ftn.model.dto.LagerListElementDTO;
import com.ftn.model.dto.WarehouseCardDTO;
import com.ftn.model.dto.WarehouseDTO;
import com.ftn.repository.WarehouseDao;
import com.ftn.service.AuthenticationService;
import com.ftn.service.WarehouseService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
 * Created by Olivera on 30.5.2017..
 */
@Service
public class WarehouseServiceImplementation implements WarehouseService {

    private final WarehouseDao warehouseDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public WarehouseServiceImplementation(WarehouseDao warehouseDao, AuthenticationService authenticationService) {
        this.warehouseDao = warehouseDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<WarehouseDTO> read() {
        final User user = authenticationService.getCurrentUser();
        switch (user.getRole()) {
            case ADMIN:
                return warehouseDao.findAll().stream().map(WarehouseDTO::new).collect(Collectors.toList());
            case EMPLOYEE:
                return warehouseDao.findByEmployeeId(user.getId()).stream().map(WarehouseDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public WarehouseDTO create(WarehouseDTO warehouseDTO) {
        if (warehouseDao.findById(warehouseDTO.getId()).isPresent()) {
            throw new BadRequestException();
        }
        final Warehouse warehouse = warehouseDTO.construct();
        warehouseDao.save(warehouse);
        return new WarehouseDTO(warehouse);
    }

    @Override
    public WarehouseDTO update(Long id, WarehouseDTO warehouseDTO) {
        final Warehouse warehouse = getWarehouseById(id);
        warehouse.merge(warehouseDTO);
        warehouseDao.save(warehouse);
        return new WarehouseDTO(warehouse);
    }

    @Override
    public void delete(Long id) {
        final Warehouse warehouse = getWarehouseById(id);
        warehouseDao.delete(warehouse);
    }

    @Override
    public String generateReport(WarehouseDTO warehouseDTO) {
        Optional<Warehouse> warehouse = warehouseDao.findById(warehouseDTO.getId());
        String jasperFilePath = "src/main/resources/jasper/LagerLista.jasper";

        ArrayList<LagerListElementDTO> tableItems = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();

        for(WarehouseCard warehouseCard :  warehouse.get().getWarehouseCards()) {
            LagerListElementDTO lagerListElementDTO = new LagerListElementDTO(warehouseCard);
            tableItems.add(lagerListElementDTO);
        }

        data.put("CompanyName", warehouseDTO.getCompany().getName());
        data.put("WarehouseName", warehouseDTO.getName());
        data.put("DataSource", new JRBeanCollectionDataSource(tableItems));

        try{
            String filePath = "src/main/resources/lagerReports/lagerLista" + getNextFileCounter() + ".pdf";
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, data, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/lagerReports/lagerLista" + getNextFileCounter() + ".pdf");

            File file = new File("C:\\Users\\Jasmina\\lagerLista.pdf");
            OutputStream outputStream = new FileOutputStream(file);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            Path path = Paths.get("C:\\Users\\Jasmina\\lagerLista.pdf");
            byte[] dataPDF = Files.readAllBytes(path);
            byte[] encodedBytes = Base64.getEncoder().encode(dataPDF);
            return new String(encodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Warehouse getWarehouseById(Long id) {
        final User user = authenticationService.getCurrentUser();
        switch (user.getRole()) {
            case ADMIN:
                return warehouseDao.findById(id).orElseThrow(NotFoundException::new);
            case EMPLOYEE:
                return warehouseDao.findByIdAndEmployeeId(id, user.getId()).orElseThrow(NotFoundException::new);
        }
        return null;
    }

    private static int getNextFileCounter() {
        int counter = 0;
        File folder = new File("src/main/resources/lagerReports");
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
