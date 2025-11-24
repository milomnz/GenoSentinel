import { Controller, Get, Post, Body, Param, Delete, ParseIntPipe, HttpCode, HttpStatus} from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse, ApiParam } from '@nestjs/swagger';
import { ClinicalRecordService } from '../services/clinicalRecord.service';
import { CreateClinicalRecordDto } from '../dto/create-clinicalRecord';
import { ClinicalRecord } from '../entities/ClinicalRecord.entity';

@ApiTags('Tipos de Tumor')
@Controller('clinical-records')
export class ClinicalRecordController{
    constructor(private readonly clinicalRecordService: ClinicalRecordService){}


    @Post()
    @HttpCode(HttpStatus.CREATED)
    @ApiOperation({
        summary: 'Crea un nuevo registro clinico',
        description: 'Registra un nuevo diagnostico de tumor, etapa y protocolo de trtamiento para el paciente'

    })

    @ApiResponse({
        status: 201,
        description: 'Registro clinico creado exitosamente',
        type: ClinicalRecord
    })

    @ApiResponse({
        status:404,
        description:'Datos de entrada invalidos (IDs o formato de fecha incorrectos)'
    })
    
    
    create(@Body() createClinicalRecordDto: CreateClinicalRecordDto): Promise<ClinicalRecord>{
        return this.clinicalRecordService.create(createClinicalRecordDto);
    }

    @ApiOperation({
        summary: 'Obtiene todos los registros clinicos',
        description: 'Decuelve una lista de todos los registros clinicos, incluyendo los datos relacionados del paciente y tipo tumor'

    })

    @ApiResponse({
        status:200,
        description: 'Lista de registros clinicos devuelta exitosamente',
        type: [ClinicalRecord]
    })

    @Get()
    findAll(): Promise<ClinicalRecord[]>{
        return this.clinicalRecordService.findAll();
    }

    @Get(':id')
    @ApiOperation({
        summary: 'Obtiene un registro clinico por ID',
        description: 'Busca un registro clinico especifico de su ID numerico'
    })

    @ApiParam({
        name: 'id',
        description: ' ID numerico del registro clinico',
        type: String
    })

    @ApiResponse({
        status: 200,
        description: 'Registro clinico encontrado exitosamente',
        type: ClinicalRecord
    })
    @ApiResponse({
        status:404,
        description: 'Regiatro clinico no encontrado'
    })
    findById(@Param('id', ParseIntPipe) id:number): Promise<ClinicalRecord>{
        return this.clinicalRecordService.findById(id);
    }

    @Delete(':id')
    @HttpCode(HttpStatus.NO_CONTENT)

    @ApiOperation({
        summary:'Elimina un registro clinico',
        description: 'Elimina permanentemente un registro clinico por su ID'
    })

    @ApiParam({
        name:'id',
        description:'ID numerico del registro clinico a eliminar',
        type:String
    })

    @ApiResponse({
        status: 204,
        description:'Registro clinico eliminado exitosamente'
    })

    @ApiResponse({
        status: 404,
        description: 'Registro clinico no encontrado'
    })
    delete(@Param('id', ParseIntPipe) id: number): Promise<void>{
        return this.clinicalRecordService.delete(id);
    }
}