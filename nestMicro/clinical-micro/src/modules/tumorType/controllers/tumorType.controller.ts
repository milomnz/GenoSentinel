import {Controller, Get, Post, Body, Param, ParseIntPipe, HttpCode, HttpStatus} from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse, ApiParam, ApiOkResponse } from '@nestjs/swagger';
import { TumorTypeService } from '../services/tumorType.service';
import { CreateTumorTypeDto } from 'src/modules/tumorType/dto/create-tumorType';
import { TumorType } from 'src/modules/tumorType/entities/TumorType.entity';
import { stat } from 'fs';

@Controller('tumor-types')
export class TumorTypeController{
    constructor(private readonly tumorTypeService: TumorTypeService){}

    @Post()
    @HttpCode(HttpStatus.CREATED)
    @ApiOperation({
        summary: 'Registra un nuevo tipo de tumor',
        description: 'Crea una nueva clasificacion histológica con el sistema afectado.'
    })
    @ApiResponse({
        status: 201,
        description: 'El tipo de tumos ha sido creado exitosamente',
        type: TumorType
    })
    @ApiResponse({
        status: 400,
        description: 'Datos de entrada invalidos'
    })
    create(@Body() createTumorTypeDto: CreateTumorTypeDto): Promise <TumorType>{
        return this.tumorTypeService.create(createTumorTypeDto);
    }

    @Get()
    @ApiOperation({
        summary: 'Obtiene todos los tipos de tumor',
        description: ' Devuelve una lista completa de todas las clasificaciones de tumores registradas'
    })
    @ApiResponse({
        status: 200,
        description: 'Lista de tipos de tumores retornada exitosamente',
        type: [TumorType]
    })
    findAll(): Promise<TumorType[]>{
        return this.tumorTypeService.findAll();
    }

    @Get(':id')
    @ApiOperation({
        summary: 'Obtiene un tipo de tumor por ID',
        description: ' Busca y devuelve la clasificacion de tumor correspondiente al ID proporcionado'
    })
    @ApiParam({
        name: 'id',
        description: 'ID numerico del tipo de tumor',
        type: Number
    })
    @ApiResponse({
        status: 200,
        description: 'Tipo de tumor encontrado exitosamente.',
        type: TumorType
    })

    @ApiResponse({
        status: 404,
        description:'Tipo de tumor no encontrado'
    })
    findById(@Param('id', ParseIntPipe) id: number): Promise<TumorType>{
        return this.tumorTypeService.findById(id);
    }
    
}